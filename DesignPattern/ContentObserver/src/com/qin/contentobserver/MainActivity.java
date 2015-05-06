package com.qin.contentobserver;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.*;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
/**
 * �ܽ᣺ ʹ��ContentObserver�������Ҫ��һ�����������
        1����ҪƵ���������ݿ����ĳ�������Ƿ����ı䣬���ʹ���߳�ȥ�������ܲ����ö��Һܺ�ʱ ��
        2�����û���֪��������¶����ݿ���һЩ�¼������磺���ķ�����Ϣ���ܾ����ܶ��ź������ȣ�
       ���ⲹ�䣬���ǿ�ҽ��鹹��ContentObserver����ʱ���������߳����ڵ�Handler�����£�
   airplaneCO = new AirplaneContentObserver(this, mHandler); // mHandlerΪUI�̵߳�Handler����  
       �����ڸ���UIʱ�����ܻᱨ�쳣(��UI�̸߳���UIʱ����SecurityException)��ϵͳ���Ľ��̣��ᵼ��
       �����ֻ���������onChange()�����У�ʹ��Handler����ط������ص���UI�̸߳���UI��ͼ��
       
        // ��Ҫ����  ---- > ContentObserver����ԭ��˵��
      Ϊʲô���ݸı���ص���ContentObserver �� Ϊʲô�����Զ����ContentProvider����Դ�����ı�
      ��ȴû�м������κη�Ӧ �� ����ϵͳ�Ļص�ϵͳ�߼��йء�
      ÿ��ContentProvider����Դ�����ı�������֪ͨ��������� ����ContentObserverʱ�����������Ӧ���� update / 
  insert / deleteʱ����ʾ�ĵ���this.getContentReslover����.notifychange(uri , null)�������ص����������߼�����������
     ��ContentObserver�ǲ�����������ݷ����ı�ġ�
     
 * @Description: TODO �۲���ģʽ
 * @author Hugs
 * @2014-11-26
 */
public class MainActivity extends Activity {

	private TextView tvAirplane;
	private EditText etSmsoutbox;

	// Message ����ֵ
	private static final int MSG_AIRPLANE = 1;
	private static final int MSG_OUTBOXCONTENT = 2;

	private AirplaneContentObserver airplaneCO;
	private SMSContentObserver smsContentObserver;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		tvAirplane = (TextView) findViewById(R.id.tvAirplane);
		etSmsoutbox = (EditText) findViewById(R.id.smsoutboxContent);

		// ������������
		airplaneCO = new AirplaneContentObserver(this, mHandler);
		smsContentObserver = new SMSContentObserver(this, mHandler);
		
		//ע�����ݹ۲���
		registerContentObservers() ;
	}

	private void registerContentObservers() {
		// ͨ������getUriFor ������� system�����"����ģʽ"�����е�Uri
		Uri airplaneUri = Settings.System.getUriFor(Settings.System.AIRPLANE_MODE_ON);
		// ע�����ݹ۲���
		getContentResolver().registerContentObserver(airplaneUri, false, airplaneCO);
		// �������ݹ۲��� ��ͨ�������ҷ���ֻ�ܼ�����Uri -----> content://sms
		// ��������������Uri ����˵ content://sms/outbox
		Uri smsUri = Uri.parse("content://sms");
		getContentResolver().registerContentObserver(smsUri, true,smsContentObserver);
	}

	private Handler mHandler = new Handler() {

		public void handleMessage(Message msg) {
			
			System.out.println("---mHanlder----");
			switch (msg.what) {
			case MSG_AIRPLANE:
				int isAirplaneOpen = (Integer) msg.obj;
				if (isAirplaneOpen != 0)
					tvAirplane.setText("����ģʽ�Ѵ�");
				else if (isAirplaneOpen == 0)
					tvAirplane.setText("����ģʽ�ѹر�");
				break;
			case MSG_OUTBOXCONTENT:
				String outbox = (String) msg.obj;
				etSmsoutbox.setText(outbox);
				break;
			default:
				break;
			}
		}
	};
}