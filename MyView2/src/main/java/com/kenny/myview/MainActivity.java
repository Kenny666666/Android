package com.kenny.myview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * 自定义view实现步骤
 *
 *  1、自定义View的属性
    2、在View的构造方法中获得我们自定义的属性
    [ 3、重写onMesure ]
    4、重写onDraw
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


}
