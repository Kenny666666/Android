package com.general.comtop.signature.adapter;

import java.util.ArrayList;
import java.util.List;

import com.general.comtop.signature.R;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * 数字签名数据adapter
 * 
 * @author shiyaofang
 * 
 */
public class SignatureImgListAdapter extends BaseAdapter {
	/** 当前上下文 */
	private Context mContext;
	/** 数据源 */
	private List<String> mSignatureList;
	/** 当前选择的数据 */
	private List<String> mCurrSelectItemList;

	/**
	 * 构造函数
	 * 
	 * @param context
	 * @param attachmentList
	 */
	public SignatureImgListAdapter(Context context, List<String> signatureList) {
		this.mContext = context;
		this.mSignatureList = signatureList;
		mCurrSelectItemList = new ArrayList<String>();
	}

	@Override
	public int getCount() {
		return null == mSignatureList ? 0 : mSignatureList.size();
	}

	@Override
	public Object getItem(int position) {
		return null == mSignatureList ? null : mSignatureList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	/**
	 * 添加数字签名附件项,并更新列表项目
	 * 
	 * @param attachment
	 */
	public void addItem(String attachment) {
		mSignatureList.add(attachment);
		this.notifyDataSetChanged();
	}

	/**
	 * 删除数字签名附件项，并更新列表项目
	 * 
	 * @param attachment
	 */
	public void delItem(List<String> attachmentList) {
		mSignatureList.removeAll(attachmentList);
		mCurrSelectItemList.removeAll(attachmentList);
		this.notifyDataSetChanged();
	}

	/**
	 * 获取当前选中的数字签名项
	 * 
	 * @return
	 */
	public List<String> getCurrSelectItemList() {
		return mCurrSelectItemList;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final int index = position;
		RelativeLayout reLayout = null;
		ImageView imgView = null;
		CheckBox chkBox = null;
		if (convertView == null) {
			reLayout = (RelativeLayout) LayoutInflater.from(mContext).inflate(R.layout.lib_signature_img_list_item, null);
		} else {
			reLayout = (RelativeLayout) convertView;
		}
		imgView = (ImageView) reLayout.findViewById(R.id.signature_list_imgview);

		chkBox = (CheckBox) reLayout.findViewById(R.id.signature_list_chkbox);
		chkBox.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				CheckBox chkBox = (CheckBox) v;
				boolean state = chkBox.isChecked();
				if (state) {
					mCurrSelectItemList.add(mSignatureList.get(index));
				} else {
					mCurrSelectItemList.remove(mSignatureList.get(index));
				}
			}
		});
		imgView.setImageURI(Uri.parse(mSignatureList.get(index)));
		return reLayout;
	}
}
