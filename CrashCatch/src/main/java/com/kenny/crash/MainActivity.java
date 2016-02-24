package com.kenny.crash;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * 全局异常捕获，友好提示并完美退出程序，异常信息在mnt/sdcard/Crash/文件中
 */
public class MainActivity extends AppCompatActivity {
    TextView mTextView;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.btn_test);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTextView.setText("崩溃咯");
            }
        });

    }


}
