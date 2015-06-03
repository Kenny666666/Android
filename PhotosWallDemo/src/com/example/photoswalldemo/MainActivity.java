package com.example.photoswalldemo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;

/**
 * 照片墙主活动，使用GridView展示照片墙。
 * 注意此demo的图片url地址是连接到google的，所以想看效果要用vpn翻墙哦,
 * 还有加载图片时没有缩放哦，如果想加载自己服务器上的大图片，最好先把图片压缩到很小，避免OOM。此demo现加载的都是几KB的图片所以不会出现OOM
 * @author guolin
 */
public class MainActivity extends Activity {

	/**
	 * 用于展示照片墙的GridView
	 */
	private GridView mPhotoWall;

	/**
	 * GridView的适配器
	 */
	private PhotoWallAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mPhotoWall = (GridView) findViewById(R.id.photo_wall);
		adapter = new PhotoWallAdapter(this, 0, Images.imageThumbUrls, mPhotoWall);
		mPhotoWall.setAdapter(adapter);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// 退出程序时结束所有的下载任务
		adapter.cancelAllTasks();
	}

}
