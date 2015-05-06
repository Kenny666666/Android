package com.demo;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
/**
 * 应用程序内部数据变更观察者模式
 * 测试方法1、首先插入一条数据
 *       2、调用更新方法、删除方法
 * @author Hugs
 * @2014-11-26
 */
public class MainActivity extends Activity {
	// url便于标识符
	// uri定位符
	private Button insertBtn;
	private Button queryBtn;
	private Button deleteBtn;
	private Button updateBtn;
	private Uri uri;
	Context mContext;
	/**
	 * 观察者类
	 * @author Administrator
	 *
	 */
	class MyObserver extends ContentObserver{

		public MyObserver(Handler handler) {
			super(handler);
		}

		@Override
		public void onChange(boolean selfChange) {
			super.onChange(selfChange);
			//在onChange中更新
			Toast.makeText(MainActivity.this, "发生变化了，快更新", 4000).show();
			//更新listView,adapter.notifyChange.....
		}
		
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mContext = this;
		// 一定要放在绑定控件方法和监听事件方法
		uri = Uri.parse("content://com.demo.QingyouDao");
		//观察者
		MyObserver myObserver = new MyObserver(new Handler());
		mContext.getContentResolver().registerContentObserver(uri, true, myObserver);
		
		insertBtn = (Button) findViewById(R.id.button1);
		queryBtn = (Button) findViewById(R.id.button2);
		deleteBtn = (Button) findViewById(R.id.button3);
		updateBtn = (Button) findViewById(R.id.button4);

		insertBtn.setOnClickListener(new InsertBtnOnClickListener());
		queryBtn.setOnClickListener(new QueryBtnOnClickListener());
		deleteBtn.setOnClickListener(new DeleteOnClickListener());
		updateBtn.setOnClickListener(new UpdateBtnOnClickListener());
	}

	// 更新
	class UpdateBtnOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			ContentValues values = new ContentValues();
			values.put("name", "小红");
			int result = MainActivity.this.getContentResolver().update(uri,
					values,null,null);
			if(result>=1){
				System.out.println("更新成功");
				mContext.getContentResolver().notifyChange(uri, null);
			}
		}

	}

	// 删除
	class DeleteOnClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			int result=MainActivity.this.getContentResolver().delete(uri, "name=?",
					new String[] { "小红" });
			if(result>=1){
				System.out.println("删除成功");	
				mContext.getContentResolver().notifyChange(uri, null);
			}
			
		}

	}

	// 插入
	class InsertBtnOnClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			ContentValues values = new ContentValues();
			values.put("name", "小丽");
			values.put("age", 33);
			MainActivity.this.getContentResolver().insert(uri, values);
			System.out.println("插入成功");
		}
	}

	// 查询
	class QueryBtnOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			Cursor cursor = MainActivity.this.getContentResolver().query(uri,
					new String[] { "name", "age" }, null, null, null);
			System.out.println(cursor.getCount());
			String name="";
			int age=0;
			while (cursor.moveToNext()) {
				 name = cursor.getString(cursor.getColumnIndex("name"));
				 age = cursor.getInt(cursor.getColumnIndex("age"));
			}
			
			Toast.makeText(MainActivity.this, name+";;;"+age + "",
					Toast.LENGTH_LONG).show();
		}

	}
}
