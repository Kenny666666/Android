package com.demo.test;

/**
 * 单例对象（Singleton）是一种常用的设计模式。在Java应用中，单例对象能保证在一个JVM中，该对象只有一个实例存在。这样的模式有几个好处：
	1、某些类创建比较频繁，对于一些大型的对象，这是一笔很大的系统开销。
	2、省去了new操作符，降低了系统内存的使用频率，减轻GC压力。
	3、有些类如交易所的核心交易引擎，控制着交易流程，如果该类可以创建多个的话，
	系统完全乱了。（比如一个军队出现了多个司令员同时指挥，肯定会乱成一团），所
	以只有使用单例模式，才能保证核心交易服务器独立控制整个流程。
	
	这个类可以满足基本要求，但是，像这样毫无线程安全保护的类，如果我们把它放入多
	线程的环境下，肯定就会出现问题了，如何解决？我们首先会想
	到对getInstance方法加synchronized关键字，如：Singleton1
 * @author Hugs
 * @2014-11-14
 */
public class Singleton {
	/* 持有私有静态实例，防止被引用，此处赋值为null，目的是实现延迟加载 */
	private static Singleton instance = null;

	/* 私有构造方法，防止被实例化 */
	private Singleton() {
	}

	/* 静态工程方法，创建实例 */
	public static Singleton getInstance() {
		if (instance == null) {
			instance = new Singleton();
		}
		return instance;
	}

	/* 如果该对象被用于序列化，可以保证对象在序列化前后保持一致 */
	public Object readResolve() {
		return instance;
	}
}
