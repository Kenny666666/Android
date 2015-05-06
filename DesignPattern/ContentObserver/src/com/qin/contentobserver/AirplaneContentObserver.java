package com.qin.contentobserver;

import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.provider.*;
import android.provider.Settings.SettingNotFoundException;
import android.util.Log;


//用来观察system表里飞行模式所在行是否发生变化 ， “行”内容观察者
public class AirplaneContentObserver extends ContentObserver {

	private static String TAG = "AirplaneContentObserver" ;
	
	private static int MSG_AIRPLANE = 1 ;
	
	private Context mContext;    
    private Handler mHandler ;  //此Handler用来更新UI线程
    
	public AirplaneContentObserver(Context context, Handler handler) {
		super(handler);
		mContext = context;
		mHandler = handler ;
	}

	/**
	 * 当所监听的Uri发生改变时，就会回调此方法
	 * 
	 * @param selfChange 此值意义不大 一般情况下该回调值false
	 */
	@Override
	public void onChange(boolean selfChange) {
        Log.i(TAG, "-------------the airplane mode has changed-------------");
        
		// 系统是否处于飞行模式下
		try {
			int isAirplaneOpen = Settings.System.getInt(mContext.getContentResolver(), Settings.System.AIRPLANE_MODE_ON);
		    Log.i(TAG, " isAirplaneOpen -----> " +isAirplaneOpen) ;
		    mHandler.obtainMessage(MSG_AIRPLANE,isAirplaneOpen).sendToTarget() ;
		}
		catch (SettingNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
