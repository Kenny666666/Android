package com.demo;

import android.app.Activity;
import android.os.Bundle;

import com.demo.factorys.SendFactory;
import com.demo.interfaces.Sender;
/**
 * 静态工厂方法模式，将上面的多个工厂方法模式里的方法置为静态的，不需要创建实例，直接调用即可。
 * 
 * 总体来说，工厂模式适合：凡是出现了大量的产品需要创建，并且具有共同的接口时，可以通过工厂方
 * 法模式进行创建。在以上的三种模式中，第一种如果传入的字符串有误，不能正确创建对象，第三种相
 * 对于第二种，不需要实例化工厂类，所以，大多数情况下，我们会选用第三种——静态工厂方法模式。
 * @author Hugs
 * @2014-11-14
 */
public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//测试
        Sender mailSender = SendFactory.produceMail();  
        mailSender.send();
        
        Sender smsSender = SendFactory.produceSms();  
        smsSender.send();
	}

}
