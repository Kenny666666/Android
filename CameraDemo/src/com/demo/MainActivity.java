package com.demo;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.demo.adapter.ImageAdapter;
import com.demo.bean.FileBean;
import com.demo.myview.HorizontalListView;
import com.demo.util.PictureUtil;

/**拍照后缩放图片显示*/
public class MainActivity extends Activity implements OnItemClickListener {
	private Context mContext;
	private static final int REQUEST_TAKE_PHOTO = 0;
	HorizontalListView listView;
	private ImageAdapter mPhotoAdapter;
	/** 当前拍照文件  文件全名*/
	private String mCurrentPhotoPath;
	private int mCount=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mContext = this;
        
        listView = (HorizontalListView) this.findViewById(R.id.horizon_listview);
        listView.setOnItemClickListener(this);
        mPhotoAdapter = new ImageAdapter(mContext, listView);
        listView.setAdapter(mPhotoAdapter);
        
        Button btnUpload = (Button)findViewById(R.id.btn_upload);
        btnUpload.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				upload();
			}
		});
    }

	/** 启动照相机应用 */
	private void takePhoto() {
		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

			// 指定存放拍摄照片的位置
			File f = createImageFile();
			takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
			startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);

	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_TAKE_PHOTO) {
			if (resultCode == Activity.RESULT_OK) {

				// 添加到图库,这样可以在手机的图库程序中看到程序拍摄的照片
				PictureUtil.galleryAddPic(mContext, mCurrentPhotoPath);
				
				mPhotoAdapter.addFile(mCurrentPhotoPath);
				mPhotoAdapter.notifyDataSetChanged();
				mCount++;
			} else {
				// 取消照相后，删除已经创建的临时文件。
				PictureUtil.deleteTempFile(mCurrentPhotoPath);
			}
		}
	}
	
	/**
	 * 把程序拍摄的照片放到 SD卡的 DCIM目录中 demo 文件夹中
	 * 照片的命名规则为：hugs_0.jpg
	 */
	private File createImageFile() {

		String imageFileName = "hugs_" + mCount + ".jpg";

		File image = new File(PictureUtil.getAlbumDir(), imageFileName);
		mCurrentPhotoPath = image.getAbsolutePath();
		return image;
	}
	

	@Override
	public void onItemClick(android.widget.AdapterView<?> arg0, View arg1,
			int position, long arg3) {
		if(mPhotoAdapter!=null){
			if( position == 0){
				takePhoto();
			}
		}
	}
	
	/**
	 * 上传到服务器
	 */
	private void upload() {

		if (mCurrentPhotoPath != null) {
			FileUploadTask task = new FileUploadTask();
			task.execute(mCurrentPhotoPath, "0");
		} else {
			Toast.makeText(this, "请先点击拍照按钮拍摄照片", Toast.LENGTH_SHORT).show();
		}
	}

	private class FileUploadTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			String filePath = params[0];
			FileBean bean = new FileBean();

			String type = params[1];// 上传图片需要压缩

			String content = null;
			if (null != type && "1".equals(type)) {
				
			} else {
				content = PictureUtil.bitmapToString(filePath,480,800);
			}
			bean.setFileContent(content);

			File f = new File(filePath);
			String fileName = f.getName();
			bean.setFileName(fileName);
			
			//=====================================http上传===============================
			
			return null;// 使用http post
		}

		@Override
		protected void onPreExecute() {
//			progressDialog = new ProgressDialog(MainActivity.this);
//			progressDialog.setMessage("正在提交,请稍候...");
//			progressDialog.show();
		}

		@Override
		protected void onPostExecute(String result) {
//			progressDialog.dismiss();
//			Toast.makeText(MainActivity.this, result, Toast.LENGTH_LONG).show();
		}

	}
}
