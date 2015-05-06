package com.demo;

import com.demo.classadapter.Adapter;
import com.demo.classadapter.Targetable;
import com.demo.interfaceadapter.SourceSub1;
import com.demo.interfaceadapter.SourceSub2;
import com.demo.interfaceadapter.Sourceable;
import com.demo.objectadapter.Source;
import com.demo.objectadapter.Targetable1;
import com.demo.objectadapter.Wrapper;

import android.app.Activity;
import android.os.Bundle;

/**
 * 适配器模式将某个类的接口转换成客户端期望的另一个接口表示，目的是消除由于接口不匹配所造成的类的兼容性问题。
 * @Description: TODO 主要分为三类：类的适配器模式、对象的适配器模式、接口的适配器模式。
 * 
 * 
 *  讲了这么多，总结一下三种适配器模式的应用场景：
类的适配器模式：当希望将一个类转换成满足另一个新接口的类时，可以使用类的适配器模式，创建一个新类，继承原有的类，实现新的接口即可。
对象的适配器模式：当希望将一个对象转换成满足另一个新接口的对象时，可以创建一个Wrapper类，持有原类的一个实例，在Wrapper类的方法中，调用实例的方法就行。
接口的适配器模式：当不希望实现一个接口中所有的方法时，可以创建一个抽象类Wrapper，实现所有方法，我们写别的类的时候，继承抽象类即可。
 * @author Hugs
 * @2014-12-1
 */
public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		testClassAdapter();
		testObjectAdapter();
		testInterfaceAdapter();
	}

	// 测试类的适配器模式
	private void testClassAdapter() {
		// 核心思想就是：有一个Source类，拥有一个方法，待适配，目标接口时Targetable，
		// 通过Adapter类，将Source的功能扩展到Targetable里
		Targetable target = new Adapter();
		target.method1();
		target.method2();
	}

	// 测试对象的适配器模式
	private void testObjectAdapter() {
		//基本思路和类的适配器模式相同，只是将Adapter类作修改，这次不继承Source类，
		//而是持有Source类的实例，以达到解决兼容性的问题。
		Source source = new Source();  
        Targetable1 target = new Wrapper(source);  
        target.method1();  
        target.method2();  
	}

	// 测试接口的适配器模式
	private void testInterfaceAdapter() {
		//接口的适配器是这样的：有时我们写的一个接口中有多个抽象方法，当我们写该接口的实现类时，
		//必须实现该接口的所有方法，这明显有时比较浪费，因为并不是所有的方法都是我们需要的，有时
		//只需要某一些，此处为了解决这个问题，我们引入了接口的适配器模式，借助于一个抽象类，该抽
		//象类实现了该接口，实现了所有的方法，而我们不和原始的接口打交道，只和该抽象类取得联系，
		//所以我们写一个类，继承该抽象类，重写我们需要的方法就行。
		Sourceable source1 = new SourceSub1();  
        Sourceable source2 = new SourceSub2();  
          
        source1.method1();  
//        source1.method2();  
//        source2.method1();  
        source2.method2(); 
	}
}
