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
 * Ӧ�ó����ڲ����ݱ���۲���ģʽ
 * ���Է���1�����Ȳ���һ������
 *       2�����ø��·�����ɾ������
 * @author Hugs
 * @2014-11-26
 */
public class MainActivity extends Activity {
	// url���ڱ�ʶ��
	// uri��λ��
	private Button insertBtn;
	private Button queryBtn;
	private Button deleteBtn;
	private Button updateBtn;
	private Uri uri;
	Context mContext;
	/**
	 * �۲�����
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
			//��onChange�и���
			Toast.makeText(MainActivity.this, "�����仯�ˣ������", 4000).show();
			//����listView,adapter.notifyChange.....
		}
		
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mContext = this;
		// һ��Ҫ���ڰ󶨿ؼ������ͼ����¼�����
		uri = Uri.parse("content://com.demo.QingyouDao");
		//�۲���
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

	// ����
	class UpdateBtnOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			ContentValues values = new ContentValues();
			values.put("name", "С��");
			int result = MainActivity.this.getContentResolver().update(uri,
					values,null,null);
			if(result>=1){
				System.out.println("���³ɹ�");
				mContext.getContentResolver().notifyChange(uri, null);
			}
		}

	}

	// ɾ��
	class DeleteOnClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			int result=MainActivity.this.getContentResolver().delete(uri, "name=?",
					new String[] { "С��" });
			if(result>=1){
				System.out.println("ɾ���ɹ�");	
				mContext.getContentResolver().notifyChange(uri, null);
			}
			
		}

	}

	// ����
	class InsertBtnOnClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			ContentValues values = new ContentValues();
			values.put("name", "С��");
			values.put("age", 33);
			MainActivity.this.getContentResolver().insert(uri, values);
			System.out.println("����ɹ�");
		}
	}

	// ��ѯ
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
