package com.demo;

import android.app.Activity;
import android.os.Bundle;

import com.demo.factorys.SendFactory;
import com.demo.interfaces.Sender;
/**
 * 多个工厂方法模式，是对普通工厂方法模式的改进，在普通工厂方法模式中，
 * 如果传递的字符串出错，则不能正确创建对象，而多个工厂方法模式是提供多个工厂方法，分别创建对象。
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
        Sender sender = factory.produceMail();  
        sender.send();  
	}

}
