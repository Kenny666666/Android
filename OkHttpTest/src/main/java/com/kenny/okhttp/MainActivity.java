package com.kenny.okhttp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kenny.okhttp.utils.OkHttpClientManager;
import com.kenny.okhttp.utils.User;
import com.squareup.okhttp.Request;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Okhttp测试
 * Created by kenny on 2015/6/21.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private final static String TAG = "MainActivity";
    private Button
            mBtGetObj,      //得到对象
            mBtGetObjs,     //得到对象集合
            btGetSimpleStr, //得到string
            btGetHtml,      //得到html
            btGetImage,     //加载图片
            btUploadFile,   //上传文件
            btDownloadFile, //下载文件
            btToActivity
                    ;
    private TextView mTvShow;
    private ImageView mIvShow;

    /**
     * 模版：
     * ResultCallback包含两个回调， onBefore 和 onAfter 。两个方法都在UI线程回调，一个在请求开始前，一个是请求结束。所以你可以在 onBefore 弹出等待框等操作， onAfter 隐藏等待框等。
     * 如果你的项目所有的框是一致的，或者可以分类，你可以按照如下方式编写几个模板：*/
    public abstract class MyResultCallback<T> extends OkHttpClientManager.ResultCallback<T> {

        @Override
        public void onBefore() {
            super.onBefore();
            setTitle("loading...");
            //加载前（可以放进度条显示的位置）
        }

        @Override
        public void onAfter() {
            super.onAfter();
            setTitle("Sample-okHttp");
            //加载完成（可以关闭进度条）
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        setListener();
    }

    private void initView(){
        mBtGetObj = (Button) findViewById(R.id.bt_get_obj);
        mBtGetObjs = (Button) findViewById(R.id.bt_get_objs);
        btGetSimpleStr = (Button) findViewById(R.id.bt_get_simple_str);
        btGetHtml = (Button) findViewById(R.id.bt_get_html);
        btGetImage = (Button) findViewById(R.id.bt_get_image);
        btUploadFile = (Button) findViewById(R.id.bt_upload_file);
        btDownloadFile = (Button) findViewById(R.id.bt_download_file);
        btToActivity = (Button) findViewById(R.id.bt_toactivity);

        mTvShow = (TextView) findViewById(R.id.tv_show);
        mIvShow = (ImageView) findViewById(R.id.iv_show);
    }

    private void setListener(){
        mBtGetObj.setOnClickListener(this);
        mBtGetObjs.setOnClickListener(this);
        btGetSimpleStr.setOnClickListener(this);
        btGetHtml.setOnClickListener(this);
        btGetImage.setOnClickListener(this);
        btUploadFile.setOnClickListener(this);
        btDownloadFile.setOnClickListener(this);
        btToActivity.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_get_obj:
                getObj();
                break;
            case R.id.bt_get_objs:
                getObjs();
                break;
            case R.id.bt_get_simple_str:
                getSimpleString();
                break;
            case R.id.bt_get_html:
                getHtml();
                break;
            case R.id.bt_get_image:
                getImage();
                break;
            case R.id.bt_upload_file:
                try {
                    uploadFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.bt_download_file:
                downloadFile();
                break;
            case R.id.bt_toactivity:
                startActivity(new Intent(MainActivity.this,ToActivity.class));
                break;
        }
    }

    public void getObj(){
        OkHttpClientManager.getAsyn("http://192.168.56.1:8080/okHttpServer/user!getUser",
                new OkHttpClientManager.ResultCallback<User>() {
                    @Override
                    public void onError(Request request, Exception e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(User u) {
                        mTvShow.setText(u.toString());
                    }

                    @Override
                    public void onBefore() {
                        super.onBefore();
                        Log.e(TAG,"请求中...");
                    }

                    @Override
                    public void onAfter() {
                        super.onAfter();
                        Log.e(TAG, "请求完成！");
                    }
                });
    }


    public void getObjs() {
        OkHttpClientManager.getAsyn("http://192.168.56.1:8080/okHttpServer/user!getUsers",
                new OkHttpClientManager.ResultCallback<List<User>>() {
                    @Override
                    public void onError(Request request, Exception e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(List<User> us) {
                        Log.e("TAG", us.size() + "");
                        mTvShow.setText(us.get(1).toString());
                    }
                    @Override
                    public void onBefore() {
                        super.onBefore();
                        Log.e(TAG, "请求中...");
                    }

                    @Override
                    public void onAfter() {
                        super.onAfter();
                        Log.e(TAG, "请求完成！");
                    }
                });


    }

    public void getSimpleString() {
        OkHttpClientManager.getAsyn("http://www.baidu.com", new OkHttpClientManager.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(String u) {
                mTvShow.setText(u);
            }
            @Override
            public void onBefore() {
                super.onBefore();
                Log.e(TAG, "请求中...");
            }

            @Override
            public void onAfter() {
                super.onAfter();
                Log.e(TAG, "请求完成！");
            }
        });


    }

    public void getHtml() {
        OkHttpClientManager.getAsyn("https://github.com/hongyangAndroid", new OkHttpClientManager.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(String u) {
                mTvShow.setText(u);
            }
            @Override
            public void onBefore() {
                super.onBefore();
                Log.e(TAG, "请求中...");
            }

            @Override
            public void onAfter() {
                super.onAfter();
                Log.e(TAG, "请求完成！");
            }
        });


    }

    public void getImage() {
        mTvShow.setText("");
        OkHttpClientManager.getDisplayImageDelegate().displayImage(mIvShow, "http://images.csdn.net/20150817/1.jpg");
    }

    public void uploadFile() throws IOException {
        File file = new File(Environment.getExternalStorageDirectory(), "test1.txt");

        if (!file.exists()) {
            Toast.makeText(MainActivity.this, "文件不存在，请修改文件路径", Toast.LENGTH_SHORT).show();
            return;
        }

        OkHttpClientManager.getUploadDelegate().postAsyn("http://192.168.1.103:8080/okHttpServer/fileUpload",//
                "mFile",//
                file,//
                new OkHttpClientManager.Param[]{
                        new OkHttpClientManager.Param("username", "zhy"),
                        new OkHttpClientManager.Param("password", "123")},//
                new MyResultCallback<String>() {
                    @Override
                    public void onError(Request request, Exception e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(String filePath) {
                        Log.e("TAG", filePath);
                    }
                    @Override
                    public void onBefore() {
                        super.onBefore();
                        Log.e(TAG, "请求中...");
                    }

                    @Override
                    public void onAfter() {
                        super.onAfter();
                        Log.e(TAG, "请求完成！");
                    }
                }
        );
    }


    public void downloadFile() {
        OkHttpClientManager.getDownloadDelegate().downloadAsyn("https://github.com/hongyangAndroid/okhttp-utils/blob/master/gson-2.2.1.jar?raw=true",
                Environment.getExternalStorageDirectory().getAbsolutePath(),
                new MyResultCallback<String>() {
                    @Override
                    public void onError(Request request, Exception e) {
                    }

                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(MainActivity.this, response + "下载成功", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onBefore() {
                        super.onBefore();
                        Log.e(TAG,"请求中...");
                    }

                    @Override
                    public void onAfter() {
                        super.onAfter();
                        Log.e(TAG, "请求完成！");
                    }
                });
    }
}
