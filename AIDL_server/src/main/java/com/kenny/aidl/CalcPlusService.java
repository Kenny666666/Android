package com.kenny.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;

/**
 * 不依赖aidl文件实现进程间通信
 */
public class CalcPlusService extends Service {

    private static final String DESCRIPTOR = "CalcPlusService";
    private static final String TAG = "CalcPlusService";

    public void onCreate() {
        Log.e(TAG, "onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    public IBinder onBind(Intent t) {
        Log.e(TAG, "onBind");
        return mBinder;
    }

    public void onDestroy() {
        Log.e(TAG, "onDestroy");
        super.onDestroy();
    }

    public boolean onUnbind(Intent intent) {
        Log.e(TAG, "onUnbind");
        return super.onUnbind(intent);
    }

    public void onRebind(Intent intent) {
        Log.e(TAG, "onRebind");
        super.onRebind(intent);
    }

    private MyBinder mBinder = new MyBinder();

    private class MyBinder extends Binder {
        @Override
        protected boolean onTransact(int code, Parcel data, Parcel reply,
                                     int flags) throws RemoteException {
            switch (code) {
                case 0x110: { //乘
                    data.enforceInterface(DESCRIPTOR);
                    int _arg0;
                    _arg0 = data.readInt();
                    int _arg1;
                    _arg1 = data.readInt();
                    int _result = _arg0 * _arg1;
                    reply.writeNoException();
                    reply.writeInt(_result);
                    return true;
                }
                case 0x111: { //除
                    data.enforceInterface(DESCRIPTOR);
                    int _arg0;
                    _arg0 = data.readInt();
                    int _arg1;
                    _arg1 = data.readInt();
                    int _result = _arg0 / _arg1;
                    reply.writeNoException();
                    reply.writeInt(_result);
                    return true;
                }
            }
            return super.onTransact(code, data, reply, flags);
        }

    };
}
