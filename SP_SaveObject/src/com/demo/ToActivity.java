package com.demo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.demo.bean.Bean;
import com.demo.util.SharedPreferencesTool;

public class ToActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.toactivity);
		
		Bean b = SharedPreferencesTool.readBean(this);
		
		((TextView)findViewById(R.id.tv_name)).setText(b.getName());
		((TextView)findViewById(R.id.tv_age)).setText(b.getAge());
	}
}
