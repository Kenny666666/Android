package com.demo;

import android.app.Activity;
import android.os.Bundle;
/**
 * 代理模式
 * @Description: TODO 比如我们在租房子的时候回去找中介，为什么呢？
 * 因为你对该地区房屋的信息掌握的不够全面，希望找一个更熟悉的人去帮你做，
 * 此处的代理就是这个意思。再如我们有的时候打官司，我们需要请律师，因为律
 * 师在法律方面有专长，可以替我们进行操作，表达我们的想法
 * @author Hugs
 * @2014-12-1
 */
public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//输出：
		//before proxy!
		//the original method!
		//after proxy!
		//代理模式的应用场景：
		//如果已有的方法在使用的时候需要对原有的方法进行改进，此时有两种办法：
		//1、修改原有的方法来适应。这样违反了“对扩展开放，对修改关闭”的原则。
		//2、就是采用一个代理类调用原有的方法，且对产生的结果进行控制。这种方法就是代理模式。
		//   使用代理模式，可以将功能划分的更加清晰，有助于后期维护！
		Sourceable source = new Proxy();  
        source.method();  
	}

}
