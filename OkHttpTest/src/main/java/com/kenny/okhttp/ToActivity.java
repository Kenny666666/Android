package com.kenny.okhttp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.kenny.okhttp.utils.OkHttpClientManager;
import com.squareup.okhttp.Request;

/**
 * description
 * Created by hugs on 2015/8/31.
 * version
 */
public class ToActivity extends AppCompatActivity{

    private final static String TAG="ToActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) this.findViewById(R.id.bt_requst);
        final TextView tv = (TextView) this.findViewById(R.id.tv_1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkHttpClientManager.getAsyn("https://github.com/hongyangAndroid", new OkHttpClientManager.ResultCallback<String>() {
                    @Override
                    public void onError(Request request, Exception e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(String u) {
                        tv.setText(u);
                    }
                    @Override
                    public void onBefore() {
                        super.onBefore();
                        Log.e(TAG, "请求中...");
                    }

                    @Override
                    public void onAfter() {
                        super.onAfter();
                        Log.e(TAG, "请求完成！");
                    }
                });
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
