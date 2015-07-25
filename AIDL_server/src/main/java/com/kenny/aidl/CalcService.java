package com.kenny.aidl;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

/**
 * 依赖aidl文件实现进程间通信
 *
 * 我们已经通过AIDL生成的代码解释了Android Binder框架的工作原理。Service的作用其实就是为我们创建Binder驱动，即服务端与客户端连接的桥梁。
 * <p/>
 * AIDL其实通过我们写的aidl文件，帮助我们生成了一个接口，一个Stub类用于服务端，一个Proxy类用于客户端调用。
 * service:
 * 然后我们继续点击12+12，50-12，通过上图可以看到，依然可以正确执行，也就是说即使onUnbind被调用，连接也是不会断开的，那么什么时候会断开呢？
 * 即当服务端被异常终止的时候，比如我们现在在手机的正在执行的程序中找到该服务：
 */
public class CalcService extends Service {
    private static final String TAG = "server";

    public void onCreate() {
        Log.e(TAG, "onCreate");
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

    private final ICalcAIDL.Stub mBinder = new ICalcAIDL.Stub() {

        @Override
        public int add(int x, int y) throws RemoteException {
            return x + y;
        }

        @Override
        public int min(int x, int y) throws RemoteException {
            return x - y;
        }

    };

}
