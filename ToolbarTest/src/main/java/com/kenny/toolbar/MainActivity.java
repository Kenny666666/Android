package com.kenny.toolbar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


/**
 * Toolbar示例基类
 *
 * @author AigeStudio 2015-07-16
 */
public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_main_toolbar);

        Button btn1 = (Button) findViewById(R.id.ac_main_toolbar_btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ToolbarForActionBarActivity.class));
            }
        });
        Button btn2 = (Button) findViewById(R.id.ac_main_toolbar_btn2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ToolbarActivity.class));
            }
        });
    }
}
