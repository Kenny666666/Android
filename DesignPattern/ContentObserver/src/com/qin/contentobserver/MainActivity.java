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
 * 总结： 使用ContentObserver的情况主要有一下两者情况：
        1、需要频繁检测的数据库或者某个数据是否发生改变，如果使用线程去操作，很不经济而且很耗时 ；
        2、在用户不知晓的情况下对数据库做一些事件，比如：悄悄发送信息、拒绝接受短信黑名单等；
       额外补充，最后强烈建议构造ContentObserver对象时，传递主线程所在的Handler，如下：
   airplaneCO = new AirplaneContentObserver(this, mHandler); // mHandler为UI线程的Handler对象  
       否则，在更新UI时，可能会报异常(非UI线程更新UI时，即SecurityException)。系统级的进程，会导致
       重启手机。或者在onChange()方法中，使用Handler类相关方法，回调到UI线程更新UI视图。
       
        // 重要补充  ---- > ContentObserver监听原理说明
      为什么数据改变后会回调至ContentObserver ？ 为什么我们自定义的ContentProvider数据源发生改变
      后，却没有监听到任何反应 ？ 这与系统的回调系统逻辑有关。
      每个ContentProvider数据源发生改变后，如果想通知其监听对象， 例如ContentObserver时，必须在其对应方法 update / 
  insert / delete时，显示的调用this.getContentReslover（）.notifychange(uri , null)方法，回调监听处理逻辑。否则，我们
     的ContentObserver是不会监听到数据发生改变的。
     
 * @Description: TODO 观察者模式
 * @author Hugs
 * @2014-11-26
 */
public class MainActivity extends Activity {

	private TextView tvAirplane;
	private EditText etSmsoutbox;

	// Message 类型值
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

		// 创建两个对象
		airplaneCO = new AirplaneContentObserver(this, mHandler);
		smsContentObserver = new SMSContentObserver(this, mHandler);
		
		//注册内容观察者
		registerContentObservers() ;
	}

	private void registerContentObservers() {
		// 通过调用getUriFor 方法获得 system表里的"飞行模式"所在行的Uri
		Uri airplaneUri = Settings.System.getUriFor(Settings.System.AIRPLANE_MODE_ON);
		// 注册内容观察者
		getContentResolver().registerContentObserver(airplaneUri, false, airplaneCO);
		// ”表“内容观察者 ，通过测试我发现只能监听此Uri -----> content://sms
		// 监听不到其他的Uri 比如说 content://sms/outbox
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
					tvAirplane.setText("飞行模式已打开");
				else if (isAirplaneOpen == 0)
					tvAirplane.setText("飞行模式已关闭");
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