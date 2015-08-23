package com.kenny.topbar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Topbar1 topbar1 = (Topbar1) findViewById(R.id.topbar);

        topbar1.setTopbarListener(new Topbar1.TopbarClickListener() {
            @Override
            public void leftOnClickListener() {
                Toast.makeText(getApplicationContext(),"left onclick",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void rightOnClickListener() {
                Toast.makeText(getApplicationContext(),"right onclick",Toast.LENGTH_SHORT).show();
            }
        });
    }


}
