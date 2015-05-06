package com.demo;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.demo.adapter.MyAdapter;
import com.demo.bean.Bean;
/**
 * 说明：通用Adapter使用方法->创建adapter时直接继承CommonAdapter就行了，Adapter中的ViewHolder类不用写
 * 在自己的adapter构造中加入布局文件的ID  如：	public MyAdapter(Context context,List<Bean> datas){
												super(context, datas,R.layout.item_listview);
											}
 * @author hugs
 *
 */
public class MainActivity extends Activity {

	private ListView mListView;
	private List<Bean> mDatas;
	/** 通用adapter */
	private MyAdapter mCommonAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initView();
		initDatas();
	}

	private void initView() {
		mListView = (ListView) findViewById(R.id.id_listview);
	}

	private void initDatas() {
		mDatas = new ArrayList<Bean>();

		Bean bean = new Bean();
		bean.title = "Android新技能";
		bean.desc = "Android打造万能的listview和gridview适配器";
		bean.time = "2014-12-12";
		bean.phone = "10086";
		Bean bean1 = new Bean();
		bean1.title = "Android新技能";
		bean1.desc = "Android打造万能的listview和gridview适配器";
		bean1.time = "2014-12-12";
		bean1.phone = "10086";
		Bean bean2 = new Bean();
		bean2.title = "Android新技能";
		bean2.desc = "Android打造万能的listview和gridview适配器";
		bean2.time = "2014-12-12";
		bean2.phone = "10086";
		Bean bean3 = new Bean();
		bean3.title = "Android新技能";
		bean3.desc = "Android打造万能的listview和gridview适配器";
		bean3.time = "2014-12-12";
		bean3.phone = "10086";
		Bean bean4 = new Bean();
		bean4.title = "Android新技能";
		bean4.desc = "Android打造万能的listview和gridview适配器";
		bean4.time = "2014-12-12";
		bean4.phone = "10086";
		Bean bean5 = new Bean();
		bean5.title = "Android新技能";
		bean5.desc = "Android打造万能的listview和gridview适配器";
		bean5.time = "2014-12-12";
		bean5.phone = "10086";
		Bean bean6 = new Bean();
		bean6.title = "Android新技能";
		bean6.desc = "Android打造万能的listview和gridview适配器";
		bean6.time = "2014-12-12";
		bean6.phone = "10086";
		Bean bean7 = new Bean();
		bean7.title = "Android新技能";
		bean7.desc = "Android打造万能的listview和gridview适配器";
		bean7.time = "2014-12-12";
		bean7.phone = "10086";
		Bean bean8 = new Bean();
		bean8.title = "Android新技能";
		bean8.desc = "Android打造万能的listview和gridview适配器";
		bean8.time = "2014-12-12";
		bean8.phone = "10086";
		Bean bean9 = new Bean();
		bean9.title = "Android新技能";
		bean9.desc = "Android打造万能的listview和gridview适配器";
		bean9.time = "2014-12-12";
		bean9.phone = "10086";
		Bean bean10 = new Bean();
		bean10.title = "Android新技能";
		bean10.desc = "Android打造万能的listview和gridview适配器";
		bean10.time = "2014-12-12";
		bean10.phone = "10086";
		mDatas.add(bean);
		mDatas.add(bean1);
		mDatas.add(bean2);
		mDatas.add(bean3);
		mDatas.add(bean4);
		mDatas.add(bean5);
		mDatas.add(bean6);
		mDatas.add(bean7);
		mDatas.add(bean8);
		mDatas.add(bean9);
		mDatas.add(bean10);
		mCommonAdapter = new MyAdapter(this, mDatas);
		mListView.setAdapter(mCommonAdapter);
	}
}
