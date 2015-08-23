package com.kenny.eventbus;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/**
 * 玩玩事件总线
 * 总结一下：register会把当前类中匹配的方法，存入一个map，而post会根据实参去map查找进行反射调用。分析这么久，一句话就说完了~~

 其实不用发布者，订阅者，事件，总线这几个词或许更好理解，以后大家问了EventBus，可以说，就是在一个单例内部维持着一个map对象存储了一堆的方法；post无非就是根据参数去查找方法，进行反射调用。


 case MainThread:

 首先去判断当前如果是UI线程，则直接调用；否则： mainThreadPoster.enqueue(subscription, event);把当前的方法加入到队列，然后直接通过handler去发送一个消息，在handler的handleMessage中，去执行我们的方法。说白了就是通过Handler去发送消息，然后执行的。

 case BackgroundThread:

 如果当前非UI线程，则直接调用；如果是UI线程，则将任务加入到后台的一个队列，最终由Eventbus中的一个线程池去调用

 executorService = Executors.newCachedThreadPool();。

 case Async:将任务加入到后台的一个队列，最终由Eventbus中的一个线程池去调用；线程池与BackgroundThread用的是同一个。

 这么说BackgroundThread和Async有什么区别呢？

 BackgroundThread中的任务，一个接着一个去调用，中间使用了一个布尔型变量handlerActive进行的控制。

 Async则会动态控制并发。
 */
public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


}
