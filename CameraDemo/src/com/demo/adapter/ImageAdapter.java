package com.demo.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.demo.R;
import com.demo.myview.HorizontalListView;
import com.demo.util.NativeImageLoader;
import com.demo.util.NativeImageLoader.NativeImageCallBack;

/**
 * 缺陷查看界面、缺陷填写界面、选择照片界面    
 * @author hugs
 */
public class ImageAdapter extends BaseAdapter {
	
	private Context mContext;
	private HorizontalListView listView;
	LayoutInflater mLayoutInflater;
	/***
	 * 用来封装ImageView的宽和高的对象
	 */
	private Point mPoint = new Point(0, 0);
	
	/** 照片文件名列表*/
	private List<String> mPhotoFileName = new ArrayList<String>();
	
	/**
	 * 构造函数
	 * @param context  上下文
	 * @param isNeedAdd  添加图片按钮是否可用
	 * @param isNeedExpand  点击图片时是否放大
	 * @param isNeedSelected 是否需要选中框
	 */
	public ImageAdapter(Context context,HorizontalListView listView) {
		mContext = context;
		this.listView =  listView;
		this.mPhotoFileName.add("lastPhoto");
		mLayoutInflater = LayoutInflater.from(mContext);
	}

	/** 添加照片 */
	public void addFile(String fileName){
		mPhotoFileName.add(fileName);
	}
	
	
	@Override
	public int getCount() {
		return mPhotoFileName.size();
	}

	@Override
	public Object getItem(int position) {
		return mPhotoFileName.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			
			convertView = mLayoutInflater.inflate(R.layout.listview_item,null);
			holder.mImgPic = (ImageView)convertView.findViewById(R.id.iv_photo);
			// 用来监听ImageView的宽和高
//			holder.mImgPic.setOnMeasureListener(new OnMeasureListener() {
//
//				@Override
//				public void onMeasureSize(int width, int height) {
//					mPoint.set(width, height);
//				}
//			});
			mPoint.set(160, 160);
			
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		if(!"lastPhoto".equals(mPhotoFileName.get(position))){//存在图片了
			
			//避免图标错位，在异步加载成功后替换回来
			holder.mImgPic.setTag(mPhotoFileName.get(position));  
			// 利用NativeImageLoader类加载本地图片，返回的bitmap已对图片缩放处理
			Bitmap bitmap = NativeImageLoader.getInstance().loadNativeImage(
					mPhotoFileName.get(position), mPoint, new NativeImageCallBack() {

						@Override
						public void onImageLoader(Bitmap bitmap, String path) {
							ImageView mImageView = (ImageView) listView.findViewWithTag(path);
							if (bitmap != null && mImageView != null) {
								int byteCount = bitmap.getByteCount();
								Log.e("bitmapSize" ,"===="+ byteCount);
								mImageView.setImageBitmap(bitmap);
								mImageView.setTag("");
							}
						}
					});

			if (null !=bitmap ) {
				holder.mImgPic.setImageBitmap(bitmap);
			} else {
				holder.mImgPic.setImageResource(R.drawable.image_friendly_no);//设置默认图片
			}
		}else if("lastPhoto".equals(mPhotoFileName.get(position))){//还未有图片
			//最后一张添加按钮
			holder.mImgPic.setImageResource(R.drawable.dfm_btn_addphoto);
		}
		return convertView;
	}

	private static class ViewHolder {
		ImageView mImgPic;		//图片
	}
	
}
