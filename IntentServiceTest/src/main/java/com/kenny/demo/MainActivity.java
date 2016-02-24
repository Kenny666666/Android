package com.kenny.demo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 在Android的开发中，凡是遇到耗时的操作尽可能的会交给Service去做，比如我们上传多张图，上传的过程用户可能将应用置于后台，然后干别的去了，我们的Activity就很可能会被杀死，所以可以考虑将上传操作交给Service去做，如果担心Service被杀，还能通过设置startForeground(int, Notification)方法提升其优先级。
 * 那么，在Service里面我们肯定不能直接进行耗时操作，一般都需要去开启子线程去做一些事情，自己去管理Service的生命周期以及子线程并非是个优雅的做法；好在Android给我们提供了一个类，叫做IntentService，我们看下注释。
 * 意思说IntentService是一个基于Service的一个类，用来处理异步的请求。你可以通过startService(Intent)来提交请求，该Service会在需要的时候创建，当完成所有的任务以后自己关闭，且请求是在工作线程处理的。
   这么说，我们使用了IntentService最起码有两个好处，一方面不需要自己去new Thread了；另一方面不需要考虑在什么时候关闭该Service了。

 大家可以看到我们可以使用IntentService非常方便的处理后台任务，屏蔽了诸多细节；而Service与Activity通信呢，我们选择了广播的方式（当然这里也可以使用LocalBroadcastManager）。

 */
public class MainActivity extends AppCompatActivity {

    public static final String UPLOAD_RESULT = "com.kenny.demo.intentservice.UPLOAD_RESULT";
    private LinearLayout mTaskContainer;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTaskContainer = (LinearLayout)findViewById(R.id.ll_container);

        registerReceiver();

        Button btnAddTask = (Button) findViewById(R.id.btn_add_task);
        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTask();
            }
        });
    }

    private void addTask() {
        //模拟路径
        String path = "/sdcard/imgs/" + (++i) +".png";
        UploadImgService.startUploadImg(this, path);

        TextView tv = new TextView(this);
        mTaskContainer.addView(tv);
        tv.setText(path + " is uploading...");
        tv.setTag(path);
    }

    private void registerReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(UPLOAD_RESULT);
        registerReceiver(uploadImgReceiver, filter);
    }

    private BroadcastReceiver uploadImgReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (UPLOAD_RESULT.equals(intent.getAction())){
                String path = intent.getStringExtra(UploadImgService.EXTRA_IMG_PATH);
                
                handleResult(path);
            }     
        }
    };

    private void handleResult(String path) {
        TextView tv = (TextView) mTaskContainer.findViewWithTag(path);
        tv.setText(path + "  upload success ~~~");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(uploadImgReceiver);
    }
}
