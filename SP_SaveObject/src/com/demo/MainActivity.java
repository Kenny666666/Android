package com.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.demo.bean.Bean;
import com.demo.util.SharedPreferencesTool;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Bean b = new Bean();
        b.setName("小白");
        b.setAge("20");
        SharedPreferencesTool.saveBean(this, b);
        
        Button bt_test = (Button) findViewById(R.id.bt_test);
        bt_test.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(MainActivity.this, ToActivity.class));
			}
		});
    }
}
