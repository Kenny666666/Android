package com.demo;

import android.app.Activity;
import android.os.Bundle;

import com.demo.factorys.SendFactory;
import com.demo.interfaces.Sender;
/**
 * 普通工厂模式，就是建立一个工厂类，对实现了同一接口的一些类进行实例的创建
 * @author Hugs
 * @2014-11-14
 */
public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//测试
		SendFactory factory = new SendFactory();  
        Sender sender = factory.produce("sms");  
        sender.send();  
	}

}
