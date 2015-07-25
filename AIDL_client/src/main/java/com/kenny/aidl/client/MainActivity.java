package com.kenny.aidl.client;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.kenny.aidl.ICalcAIDL;


/**
 * 本demo实现进程间通信：实现方法：1、依赖aidl文件实现:生成aidl的java文件方式：选择aidl文件，build->Make project。关键性的一步，确认aidl文件所在的包名和AndroidMainifest.xml的package名是否一致。如果一致，点击 Build-->Make Project，生成相应的java文件。如果不一致，则改aidl的包名，改成一致，再点击生成，

 *                              2、不依赖aidl文件实现
 *                              3、使用Messenger对象实现(内部其实是封装aidl实现)
 */
public class MainActivity extends Activity {
    private ICalcAIDL mCalcAidl;

    private ServiceConnection mServiceConn = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e("client", "onServiceDisconnected");
            mCalcAidl = null;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e("client", "onServiceConnected");
            mCalcAidl = (ICalcAIDL) ICalcAIDL.Stub.asInterface(service);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void toactivity(View view){
        Intent intent = new Intent(MainActivity.this,ToActivity.class);
        startActivity(intent);
    }

    /**
     * 点击BindService按钮时调用
     *
     * @param view
     */
    public void bindService(View view) {
        Intent intent = new Intent();
        intent.setAction("com.kenny.aidl");
        bindService(intent, mServiceConn, Context.BIND_AUTO_CREATE);
    }

    /**
     * 点击unBindService按钮时调用
     *
     * @param view
     */
    public void unbindService(View view) {
        unbindService(mServiceConn);
    }

    /**
     * 点击12+12按钮时调用
     *
     * @param view
     */
    public void addInvoked(View view) throws Exception {

        if (mCalcAidl != null) {
            int addRes = mCalcAidl.add(12, 12);
            Toast.makeText(this, addRes + "", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "服务器被异常杀死，请重新绑定服务端", Toast.LENGTH_SHORT)
                    .show();

        }

    }

    /**
     * 点击50-12按钮时调用
     *
     * @param view
     */
    public void minInvoked(View view) throws Exception {

        if (mCalcAidl != null) {
            int addRes = mCalcAidl.min(58, 12);
            Toast.makeText(this, addRes + "", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "服务端未绑定或被异常杀死，请重新绑定服务端", Toast.LENGTH_SHORT)
                    .show();

        }

    }

}
