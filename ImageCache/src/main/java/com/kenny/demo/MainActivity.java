package com.kenny.demo;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 硬盘缓存最佳方案
 * 其它API
 除了写入缓存、读取缓存、移除缓存之外，DiskLruCache还提供了另外一些比较常用的API，我们简单学习一下。
 1. size()
 这个方法会返回当前缓存路径下所有缓存数据的总字节数，以byte为单位，如果应用程序中需要在界面上显示当前缓存数据的总大小，就可以通过调用这个方法计算出来。比如网易新闻中就有这样一个功能，如下图所示：
 2.flush()
 这个方法用于将内存中的操作记录同步到日志文件（也就是journal文件）当中。这个方法非常重要，因为DiskLruCache能够正常工作的前提就是要依赖于journal文件中的内容。前面在讲解写入缓存操作的时候我有调用过一次这个方法，但其实并不是每次写入缓存都要调用一次flush()方法的，频繁地调用并不会带来任何好处，只会额外增加同步journal文件的时间。比较标准的做法就是在Activity的onPause()方法中去调用一次flush()方法就可以了。
 3.close()
 这个方法用于将DiskLruCache关闭掉，是和open()方法对应的一个方法。关闭掉了之后就不能再调用DiskLruCache中任何操作缓存数据的方法，通常只应该在Activity的onDestroy()方法中去调用close()方法。
 4.delete()
 这个方法用于将所有的缓存数据全部删除，比如说网易新闻中的那个手动清理缓存功能，其实只需要调用一下DiskLruCache的delete()方法就可以实现了。
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private DiskLruCache mDiskLruCache = null;
    private Button btnLoadImage;
    private Button btnReadImage;
    private Button btnRemoveImage;
    private ImageView ivImage;

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_load_image://通过网络加载图片并缓存到硬盘文件中
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{

                            String imageUrl = "http://img.my.csdn.net/uploads/201309/01/1378037235_7476.jpg";
                            String key = hashKeyForDisk(imageUrl);
                            DiskLruCache.Editor editor = mDiskLruCache.edit(key);
                            if (editor != null){
                                OutputStream outputStream = editor.newOutputStream(0);
                                if (downloadUrlToStream(imageUrl,outputStream)){
                                    editor.commit();
                                }else {
                                    editor.abort();
                                }
                            }
                            mDiskLruCache.flush();
                            Looper.prepare();
                            Toast.makeText(MainActivity.this,"图片加载并缓存成功",Toast.LENGTH_LONG).show();
                            Looper.loop();
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                }).start();
                break;
            case R.id.btn_read_image://读取缓存文件中的图片
                try {
                    String imageUrl = "http://img.my.csdn.net/uploads/201309/01/1378037235_7476.jpg";
                    String key = hashKeyForDisk(imageUrl);
                    DiskLruCache.Snapshot snapShot = mDiskLruCache.get(key);
                    if (snapShot != null) {
                        InputStream is = snapShot.getInputStream(0);
                        Bitmap bitmap = BitmapFactory.decodeStream(is);
                        ivImage.setImageBitmap(bitmap);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_remove_image://移除缓存图片
                removeCache("http://img.my.csdn.net/uploads/201309/01/1378037235_7476.jpg");
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnLoadImage = (Button)this.findViewById(R.id.btn_load_image);
        btnLoadImage.setOnClickListener(this);
        btnReadImage = (Button)this.findViewById(R.id.btn_read_image);
        btnReadImage.setOnClickListener(this);
        btnRemoveImage = (Button)this.findViewById(R.id.btn_remove_image);
        btnRemoveImage.setOnClickListener(this);
        ivImage = (ImageView)this.findViewById(R.id.iv_image);

        initDiskLruCache();
    }

    /**
     * 初始化硬盘缓存
     */
    private void initDiskLruCache(){
        try{
            File cacheDir = getDiskCacheDir(this,"bitmap");
            if (!cacheDir.exists()){
                cacheDir.mkdirs();
            }
            //第一个参数指定的是数据的缓存地址，
            // 第二个参数指定当前应用程序的版本号，
            // 第三个参数指定同一个key可以对应多少个缓存文件，基本都是传1，
            // 第四个参数指定最多可以缓存多少字节的数据。
            mDiskLruCache = DiskLruCache.open(cacheDir,getAppVersion(this),1,10*1024*1024);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * 获取缓存文件
     * @param context
     * @param uniqueName
     * @return
     */
    public File getDiskCacheDir(Context context,String uniqueName){
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) || !Environment.isExternalStorageRemovable()){
            //当SD卡存在或者SD卡不可被移除的时候，取 /sdcard/Android/data/<application package>/cache
            cachePath = context.getExternalCacheDir().getPath();
        }else {
            //否则取/data/data/<application package>/cache
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }

    /**
     * 获取应用程序版本号
     * 需要注意的是，每当版本号改变，缓存路径下存储的所有数据都会被清除掉，因为DiskLruCache认为当应用程序有版本更新的时候，所有的数据都应该从网上重新获取。
     * @param context
     * @return
     */
    public int getAppVersion(Context context){
        try{
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(),0);
            return info.versionCode;
        }catch (PackageManager.NameNotFoundException e){
            e.printStackTrace();
        }
        return 1;
    }

    /**
     * httpUrlConnection加载图片方法
     * @param urlString
     * @param outputStream
     * @return
     */
    private boolean downloadUrlToStream(String urlString,OutputStream outputStream){
        HttpURLConnection urlConnection = null;
        BufferedOutputStream out = null;
        BufferedInputStream in = null;
        try{
            final URL url = new URL(urlString);
            urlConnection = (HttpURLConnection)url.openConnection();
            in = new BufferedInputStream(urlConnection.getInputStream(), 8* 1024);
            out = new BufferedOutputStream(outputStream,8*1024);
            int b;
            while ((b = in.read()) != -1){
                out.write(b);
            }
            return true;
        }catch (final IOException e){
            e.printStackTrace();
        }finally {
            if (urlConnection != null){
                urlConnection.disconnect();
            }
            try{
                if (out != null){
                    out.close();
                }
                if (in !=null){
                    in.close();
                }
            }catch (final IOException e){
                e.printStackTrace();
            }
        }
        return  false;
    }

    /**
     * 生成缓存图片名方案：将图片url通过MD5编码生成key
     * @param key
     * @return
     */
    public String hashKeyForDisk(String key){
        String cacheKey;
        try{
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        }catch (NoSuchAlgorithmException e){
            cacheKey = String.valueOf(key.hashCode());
        }
        return cacheKey;
    }

    private String bytesToHexString(byte[] bytes){
        StringBuffer sb = new StringBuffer();
        for (int i=0;i<bytes.length;i++){
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1){
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    /**
     * 移除缓存
     * 用法虽然简单，但是你要知道，这个方法我们并不应该经常去调用它。因为你完全不需要担心缓存的数据过多从而占用SD卡太多空间的问题，DiskLruCache会根据我们在调用open()方法时设定的缓存最大值来自动删除多余的缓存。只有你确定某个key对应的缓存内容已经过期，需要从网络获取最新数据的时候才应该调用remove()方法来移除缓存。
     * @param url
     */
    private void removeCache(String url){
        try {
            String imageUrl = url;
            String key = hashKeyForDisk(imageUrl);
            boolean removeResult = mDiskLruCache.remove(key);
            if (removeResult){
                Toast.makeText(MainActivity.this,"移除缓存图片成功",Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(MainActivity.this,"移除缓存图片失败",Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
