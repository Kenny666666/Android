package com.kenny.messenger;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * 此demo是通过使用Messenger进行进程间通信
 * 本工程为server服务端
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


}
