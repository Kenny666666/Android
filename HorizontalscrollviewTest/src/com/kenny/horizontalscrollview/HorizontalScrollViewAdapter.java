package com.kenny.horizontalscrollview;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class HorizontalScrollViewAdapter {
	private Context mContext;
	private LayoutInflater mInflater;
	private List<Integer> mDatas;
	
	public HorizontalScrollViewAdapter(Context context,List<Integer> datas){
		this.mContext = context;
		mInflater = LayoutInflater.from(context);
		this.mDatas = datas;
	}

	public int getCount() {
		return mDatas.size();
	}

	public Object getItem(int position) {
		return mDatas.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.listview_item,parent, false);
			viewHolder.mImg = (ImageView) convertView.findViewById(R.id.id_image);
			viewHolder.mText = (TextView) convertView.findViewById(R.id.id_text);
			
			convertView.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.mImg.setImageResource(mDatas.get(position));
		viewHolder.mText.setText("测试");
		return convertView;
	}
	
	private class ViewHolder{
		ImageView mImg;
		TextView mText;
	}
}
