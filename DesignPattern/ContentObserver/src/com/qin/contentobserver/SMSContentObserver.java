package com.qin.contentobserver;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;


//�����۲�ϵͳ�����Ϣ�����ݿ�仯  �������ݹ۲���,ֻҪ��Ϣ���ݿⷢ���仯�����ᴥ����ContentObserver ������
public class SMSContentObserver extends ContentObserver {
    private static String TAG = "SMSContentObserver";
	
    private int MSG_OUTBOXCONTENT = 2 ;
    
	private Context mContext  ;
	private Handler mHandler ;   //����UI�߳�
	
	public SMSContentObserver(Context context,Handler handler) {
		super(handler);
		mContext = context ;
		mHandler = handler ;
	}
	/**
	 * ����������Uri�����ı�ʱ���ͻ�ص��˷���
	 * 
	 * @param selfChange  ��ֵ���岻�� һ������¸ûص�ֵfalse
	 */
    @Override
	public void onChange(boolean selfChange){
    	Log.i(TAG, "the sms table has changed");
    	
		//��ѯ�������������    	
    	Uri outSMSUri = Uri.parse("content://sms/sent") ;
    	
    	Cursor c = mContext.getContentResolver().query(outSMSUri, null, null, null,"date desc");
    	if(c != null){
    		
        	Log.i(TAG, "the number of send is"+c.getCount()) ;
        	
        	StringBuilder sb = new StringBuilder() ;
        	//ѭ������
        	while(c.moveToNext()){
//        		sb.append("�������ֻ�����: "+c.getInt(c.getColumnIndex("address")))
//        		  .append("��Ϣ����: "+c.getInt(c.getColumnIndex("body")))
//        		  .append("�Ƿ�鿴: "+c.getInt(c.getColumnIndex("read"))) 
//        		  .append("����ʱ�䣺 "+c.getInt(c.getColumnIndex("date")))
//        		  .append("\n");
        		sb.append("�������ֻ�����: "+c.getInt(c.getColumnIndex("address")))
      		      .append("��Ϣ����: "+c.getString(c.getColumnIndex("body")))
      		      .append("\n");
        	}
        	c.close();       	
        	mHandler.obtainMessage(MSG_OUTBOXCONTENT, sb.toString()).sendToTarget();       	
    	}
	}
	
}
