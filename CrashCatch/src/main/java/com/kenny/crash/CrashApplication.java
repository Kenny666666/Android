package com.kenny.crash;

import android.app.Application;

/**
 * kenny
 */
public class CrashApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		CustomCrashHandler mCustomCrashHandler = CustomCrashHandler.getInstance();
		mCustomCrashHandler.setCustomCrashHanler(getApplicationContext());
	}

}
