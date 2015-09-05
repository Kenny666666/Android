package com.kenny.flowlayout;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.kenny.flowlayout.myflow.FlowLayout;


public class MainActivity extends ActionBarActivity {

	private String[] mVals = new String[]{
			"sdfsdf","dsffsdfsd","dsffsdfsd","fsdfsd","dsffsdfsd","f","dddd","sss","ffdsf","dsffsdfsd","fsdfsdfsdsdf","dsffsdfsd"
	};
	
	private FlowLayout mFlowLayout;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        mFlowLayout = (FlowLayout) this.findViewById(R.id.flow);
        initData();
    }

	private void initData() {
//		for (int i = 0; i < mVals.length; i++) {
//			Button b = new Button(this);
//			MarginLayoutParams lp = new MarginLayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
//			b.setText(mVals[i]);
//			mFlowLayout.addView(b,lp);
//		}
		LayoutInflater mInflater = LayoutInflater.from(this);
		
		for (int i = 0; i < mVals.length; i++) {
			
			TextView tv = (TextView) mInflater.inflate(R.layout.tv, mFlowLayout,false);
			
			tv.setText(mVals[i]);
			mFlowLayout.addView(tv);
		}
	}


 
}
