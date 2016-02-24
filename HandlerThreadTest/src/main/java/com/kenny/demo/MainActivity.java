package com.kenny.demo;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.widget.TextView;


/**
 * 本案使用HandlerThread模拟实时更新股票市值
 * Handler  VS   timer 的优势
 * 1、易用性：可重复执行
               •Handler可以重复执行某个任务。
               •Timer若在某个任务执行/取消之后，再次执行则会抛出一个IllegalStateException异常。为了避免这个异常，需要重新创建一个Timer对象。
            周期可调整
               若想要执行一个越来越快的定时任务，Handler可以做到，而Timer则消耗较大。
            UI界面更新
               •Handler：在创建的时候可以指定所在的线程，一般在Activity中构建的，即主线程上，所以可以在回调方法中很方便的更新界面。
               •Timer：异步回调，所以必须借助Handler去更新界面，不方便。
               既然都得用Handler去更新界面了，为何不如把定时的功能也交给Handler去做呢？

 * 2、内存占比 他们创建执行任务过程中调用的所有对象内存占比为 1：10  左右
 */
public class MainActivity extends AppCompatActivity {

    private TextView id_textview;
    private HandlerThread mCheckMsgThread;
    private Handler mCheckMsgHandler;
    private boolean isUpdateInfo;
    private static final int MSG_UPDATE_INFO=0x110;

    //管理UI线程的handler
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //创建后台线程
        initBackThread();

        id_textview = (TextView) findViewById(R.id.id_textview);
    }

    private void initBackThread() {
        mCheckMsgThread = new HandlerThread("check-message-coming");
        mCheckMsgThread.start();

        //handlerThread内部在获取looper时已经为我们做了异步处理不用考虑同步问题
        mCheckMsgHandler = new Handler(mCheckMsgThread.getLooper()){
            @Override
            public void handleMessage(Message msg) {
                //从服务器获取数据
                checkForUpdate();

                if (isUpdateInfo){
                    //每隔一段时间更新一次UI
                    mCheckMsgHandler.sendEmptyMessageDelayed(MSG_UPDATE_INFO,1000);
                }
            }
        };
    }

    /**模拟从服务器解析数据*/
    private void checkForUpdate() {
        try {
            //模拟耗时操作
            Thread.sleep(1000);
            mHandler.post(new Runnable() {
                @Override
                public void run() {

                    String result = "实时更新中，当前大盘指数：<font color='red'>%d</font>";
                    result = String.format(result , (int)(Math.random() * 3000 + 1000));
                    id_textview.setText(Html.fromHtml(result));
                }
            });
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //开始查询
        isUpdateInfo = true;
        mCheckMsgHandler.sendEmptyMessage(MSG_UPDATE_INFO);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //停止查询
        isUpdateInfo = false;
        mCheckMsgHandler.removeMessages(MSG_UPDATE_INFO);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //释放资源
        mCheckMsgThread.quit();
    }
}
