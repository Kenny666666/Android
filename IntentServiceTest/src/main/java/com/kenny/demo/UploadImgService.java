package com.kenny.demo;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * description
 * Created by hugs on 2015/8/1.
 * version
 * IntentService可以看到它在onCreate里面初始化了一个HandlerThread与handler，所以已经封装了同步的功能
 */
public class UploadImgService extends IntentService {

    private static final String ACTION_UPLOAD_IMG = "com.kenny.demo.intentservice.action.UPLOAD_IMAGE";
    public static final String EXTRA_IMG_PATH="com.kenny.demo.intentservice.extra.IMG_PATH";

    public UploadImgService() {
        super("UploadImgService");
    }

    public static void startUploadImg(Context context,String path){
        Intent intent = new Intent(context,UploadImgService.class);
        intent.setAction(ACTION_UPLOAD_IMG);
        intent.putExtra(EXTRA_IMG_PATH,path);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null){
            final String action = intent.getAction();
            if (ACTION_UPLOAD_IMG.equals(action)){
                final String path = intent.getStringExtra(EXTRA_IMG_PATH);
                handleUploadImg(path);
            }
        }
    }

    private void handleUploadImg(String path) {
        try {
            //模拟上传耗时操作
            Thread.sleep(3000);
            //使用广播将上传完成的结果发送到activity中
            Intent intent = new Intent(MainActivity.UPLOAD_RESULT);
            intent.putExtra(EXTRA_IMG_PATH,path);

            sendBroadcast(intent);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("TAG","onCreate");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("TAG", "onDestroy");
    }
}
