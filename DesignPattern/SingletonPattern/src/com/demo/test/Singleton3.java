package com.demo.test;

/**
 * 实际情况是，单例模式使用内部类来维护单例的实现，JVM内部的机制能够保证当一个类被加载的时候，
 * 这个类的加载过程是线程互斥的。这样当我们第一次调用getInstance的时候，JVM能够帮我们保证
 * instance只被创建一次，并且会保证把赋值给instance的内存初始化完毕，这样我们就不用担心上
 * 面的问题。同时该方法也只会在第一次调用的时候使用互斥机制，这样就解决了低性能问题。
 * 这样我们暂时总结一个完美的单例模式。
 * 
 * 其实说它完美，也不一定，如果在构造函数中抛出异常，实例将永远得不到创建，也会出错。所以说，十
 * 分完美的东西是没有的，我们只能根据实际情况，选择最适合自己应用场景的实现方法。也有人这样实现：
 * 因为我们只需要在创建类的时候进行同步，所以只要将创建和getInstance()分开，单独为创建加
 * synchronized关键字，也是可以的：Singleton4
 * @author Hugs
 * @2014-11-14
 */
public class Singleton3 {
	/* 私有构造方法，防止被实例化 */  
    private Singleton3() {  
    }  
  
    /* 此处使用一个内部类来维护单例 */  
    private static class SingletonFactory {  
        private static Singleton3 instance = new Singleton3();  
    }  
  
    /* 获取实例 */  
    public static Singleton3 getInstance() {  
        return SingletonFactory.instance;  
    }  
  
    /* 如果该对象被用于序列化，可以保证对象在序列化前后保持一致 */  
    public Object readResolve() {  
        return getInstance();  
    }  
}
