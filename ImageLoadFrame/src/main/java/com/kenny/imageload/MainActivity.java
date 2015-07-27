package com.kenny.imageload;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.kenny.imageload.util.ImageLoader;
import com.kenny.imageload.util.Images;

/**
 * 引用网上鸿洋大神的网络加载框架，修复了个小bug（在加载图片时如果程序被强杀，导致正在加载的图片有问题。重新进入程序后程序不会重新去网络上加载图片）
 * 修复方案：在加载硬盘缓存时如果出现异常或者图片返回为null，则直接根据图片路径删除本地图片，重新创建任务加载图片。
 * kenny
 * 关于加载网络图片，其实原理差不多，就多了个是否启用硬盘缓存的选项，如果启用了，加载时，先从内存中查找，然后从硬盘上找，最后去网络下载。下载完成后，别忘了写入硬盘，加入内存缓存。如果没有启用，那么就直接从网络压缩获取，加入内存即可。
 */
public class MainActivity extends AppCompatActivity {
    private Context mContext;
    private GridView mGridView;
    private String[] mUrlStrs = Images.imageThumbUrls;
    private ImageLoader mImageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;
        mImageLoader = ImageLoader.getInstance(3, ImageLoader.Type.LIFO);
        mGridView = (GridView) findViewById(R.id.id_gridview);
        setUpAdapter();

    }

    private void setUpAdapter() {
        if (mContext == null || mGridView == null)
            return;

        if (mUrlStrs != null) {
            mGridView.setAdapter(new ListImgItemAdaper(mContext, 0, mUrlStrs));
        } else {
            mGridView.setAdapter(null);
        }

    }

    private class ListImgItemAdaper extends ArrayAdapter<String> {
        private LayoutInflater mInflater;
        public ListImgItemAdaper(Context context, int resource, String[] datas) {
            super(context, 0, datas);
            mInflater = LayoutInflater.from(context);
            Log.e("TAG", "ListImgItemAdaper");
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = mInflater.inflate( R.layout.image_item, parent, false);
            }
            ImageView imageview = (ImageView) convertView.findViewById(R.id.id_img);
            imageview.setImageResource(R.drawable.pictures_no);
            mImageLoader.loadImage(getItem(position), imageview, true);
            return convertView;
        }

    }

}


