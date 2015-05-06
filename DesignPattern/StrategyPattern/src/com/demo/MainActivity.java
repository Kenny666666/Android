package com.demo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
/**
 * 策略模式实例
 * 	比如排序，官方告诉大家我这里有一个排序的接口ISort的sort()方法,然后民间各尽其能，实现这个排序的方法：冒泡，快速，堆等等。
	这些方法就是“不同的策略”。
	然后，某个模块下，需要一个排序方法，但是暂时不能指定具体的sort方法(出于扩展的考虑)，就需要使用ISort接口了。
	最后，具体什么场景下，传入什么具体的sort方法，实现灵活的排序。
	这就是策略模式！
	
	策略模式其实就是多态的一个淋漓精致的体现。
	3. 效果
	(1).行为型模式
	(2).消除了一些if...else...的条件语句
	(3).客户可以对实现进行选择,但是客户必须要了解这个不同策略的实现(这句话好像是废话，总而言之，客户需要学习成本)
 * @author Hugs
 * @2014-11-14
 */
public class MainActivity extends Activity {
	private TextView tv_show;
	private Button bt_bubbling_sort;
	private Button bt_insert_sort;
	private Button bt_quick_sort;
	
	private int[] arrays = {17,30,30,2,66,33,15,88};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		tv_show = (TextView) this.findViewById(R.id.tv_show);
		bt_bubbling_sort = (Button) this.findViewById(R.id.bt_bubbling_sort);
		bt_insert_sort = (Button) this.findViewById(R.id.bt_insert_sort);
		bt_quick_sort = (Button) this.findViewById(R.id.bt_quick_sort);
		
		bt_bubbling_sort.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO 我要冒泡排序
				BubblingSort sort = new BubblingSort();
				sort.getSortMethod(arrays);
				
				show(arrays);
				
			}
		});
		
		bt_insert_sort.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO 我要插入排序	
				InsertSort sort = new InsertSort();
				sort.getSortMethod(arrays);
				
				show(arrays);
			}
		});
		
		bt_quick_sort.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO 我要快速排序
				QuickSort sort = new QuickSort();
				sort.getSortMethod(arrays);
				
				show(arrays);
			}
		});
	}
	
	private void show(int[] arrays){
		String temp = "";
		for (int i = 0; i < arrays.length; i++) {
			temp +=arrays[i]+",";
		}
		tv_show.setText(temp);
	}

}
