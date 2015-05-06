package com.demo.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;

import com.demo.R;
import com.demo.bean.Bean;
/**
 * 继承通用Adapter
 * @author hugs
 *
 */
public class MyAdapter extends CommonAdapter<Bean> {

	
	public MyAdapter(Context context,List<Bean> datas){
		super(context, datas,R.layout.item_listview);
	}

	@Override
	public void convert(CommonViewHolder holder, final Bean bean) {
		
//		holder.setText(R.id.id_title, bean.title);
//		holder.setText(R.id.id_desc, bean.desc);
//		holder.setText(R.id.id_time, bean.time);
//		holder.setText(R.id.id_phone, bean.phone);
		
		//链式编程写法
		holder
		.setText(R.id.id_title, bean.title)
		.setText(R.id.id_desc, bean.desc)
		.setText(R.id.id_time, bean.time)
		.setText(R.id.id_phone, bean.phone);
		
		final CheckBox cb = holder.getView(R.id.id_cb);
		
		
		/**bean中的isChecked标记CheckBox是否被选中，解决了当选中某一个CheckBox后
		然后下翻listview会出现其它checkBox被选中的问题*/
		
		//第一种解决方法，需要bean中有一个字段可以记录状态
		cb.setChecked(bean.isChecked);
		cb.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				bean.isChecked = cb.isChecked();
			}
		});
		
		//第二种解决方法，需要一个List<Integer> mPos来记录他，不需要bean中有字段来记录状态
//		cb.setChecked(false);
//		if (mPos.contains(holder.getPosition())) {
//			cb.setChecked(true);
//		}
//		cb.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				if (cb.isChecked()) {
//					
//					mPos.add(holder.getPosition());
//				}else {
//					mPos.remove((Integer)holder.getPosition());
//				}
//			}
//		});
	}

}
