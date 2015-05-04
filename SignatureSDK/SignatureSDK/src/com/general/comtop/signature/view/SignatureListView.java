package com.general.comtop.signature.view;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.general.comtop.signature.R;
import com.general.comtop.signature.adapter.SignatureImgListAdapter;
import com.general.comtop.signature.utility.Constants;
import com.general.comtop.signature.utility.SignatureHelper;
import com.general.comtop.signature.utility.SignatureHelper.IOnConfirmClickListener;

/**
 * 电子签名列表View
 * 
 * @author shiyaofang
 * 
 */
public class SignatureListView extends LinearLayout implements View.OnClickListener, IOnConfirmClickListener {

	/** 签名按钮 */
	private TextView btnSig;
	/** 刪除签名按钮 */
	private TextView btnDel;
	/** 当前上下文 */
	private Context context;
	/** 电子签名adapter */
	private SignatureImgListAdapter sigAdapter;
	/** 存放图片的view */
	private GridView mSignatureShowGlery;
	/** 水平的scrollview */
	private HorizontalScrollView horiScrollView;

	/**
	 * 构造函数
	 * 
	 * @param context
	 * @param attrs
	 */
	public SignatureListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		View view = LayoutInflater.from(context).inflate(R.layout.lib_view_signature_listview, this);
		initViews(view);
	}

	/**
	 * 初始化控件
	 * 
	 * @param view
	 */
	private void initViews(View view) {
		btnSig = (TextView) view.findViewById(R.id.btn_sig);
		btnDel = (TextView) view.findViewById(R.id.btn_del);
		mSignatureShowGlery = (GridView) findViewById(R.id.digital_signature_show_grid);
		horiScrollView = (HorizontalScrollView) findViewById(R.id.digital_signature_show);
		initListener();
	}

	/**
	 * 设置电子签名的适配器(根据具体的业务情况来获取数据源)
	 * 
	 * @param list
	 */
	public void setSignatureAdapter(List<String> list) {
		if (null == sigAdapter) {
			sigAdapter = new SignatureImgListAdapter(context, list);
		}
		mSignatureShowGlery.setAdapter(sigAdapter);
		setSigShowLayout();
	}

	/**
	 * 初始化监听器
	 */
	private void initListener() {
		btnSig.setTag(android.R.string.ok);
		btnSig.setOnClickListener(this);

		btnDel.setTag(android.R.string.no);
		btnDel.setOnClickListener(this);
	}

	/**
	 * 从sd卡中获取电子签名的列表
	 */
	public List<String> getSignatureListFromSDCard() {
		List<String> list = new ArrayList<String>();
		File file = new File(Constants.SDCARD_PATH + SignatureHelper.getInstance(context).getSaveSignaturePath());
		if (!file.exists()) {
			file.mkdirs();
		}
		File[] files = file.listFiles();
		if (null != files && files.length > 0) {
			for (File f : files) {
				list.add(f.getAbsolutePath());
			}
		}
		return list;
	}

	/**
	 * 设置电子签名显示布局
	 */
	private void setSigShowLayout() {
		mSignatureShowGlery.getLayoutParams().width = 200 * sigAdapter.getCount();
		mSignatureShowGlery.setNumColumns(sigAdapter.getCount());
	}

	/**
	 * 滑动界面至最右边
	 */
	private void scrollUI2Right() {
		horiScrollView.postDelayed(new Runnable() {
			@Override
			public void run() {
				horiScrollView.scrollTo(horiScrollView.getMeasuredWidth(), 0);
			}
		}, 0);
	}

	@Override
	public void onClick(View v) {
		switch ((Integer) v.getTag()) {
		// 签名
		case android.R.string.ok:
			if (context != null && !((Activity) context).isFinishing()) {
				SignatureHelper.getInstance(context).showSigDialog();
				SignatureHelper.getInstance(context).setOnConfirmClickListener(this);
			}
			break;
		// 删除
		case android.R.string.no:
			if (null != sigAdapter) {
				List<String> selectItemList = sigAdapter.getCurrSelectItemList();
				if (null == selectItemList || selectItemList.size() == 0) {
					Toast.makeText(context, "请选择要删除的签名", Toast.LENGTH_SHORT).show();
					return;
				}
				// 请求执行回调方法
				SignatureHelper.getInstance(context).deleteSigPic(selectItemList);

				// 删除数据并更新界面的内容
				sigAdapter.delItem(selectItemList);
				setSigShowLayout();
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void onConfirmClick() {
		if (null == sigAdapter) {
			Toast.makeText(context, "请先为电子签名的列表设置adapter", Toast.LENGTH_SHORT).show();
			return;
		}

		/** 保存图片到本地，并设置参数 */
		String path = SignatureHelper.getInstance(context).saveSigPic(SignatureHelper.getInstance(context).getSaveSignaturePath());
		if (null != path && path.length() > 0) {
			SignatureHelper.getInstance(context).setStandardBitmapSize(300, 100);
			// 然后添加图片到界面布局
			sigAdapter.addItem(path);
			setSigShowLayout();

			scrollUI2Right();
		}
	}
}
