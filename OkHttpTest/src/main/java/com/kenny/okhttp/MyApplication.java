package com.kenny.okhttp;

import android.app.Application;

public class MyApplication extends Application {
    public void onCreate() {
        super.onCreate();


        /*
        自签名网站https的访问
        非常简单，拿到xxx.cert的证书。

        然后调用

        OkHttpClientManager.getInstance()
                .setCertificates(inputstream);

                建议使用方式，例如我的证书放在assets目录：

                 try
                {
                    OkHttpClientManager.getInstance()
                            .setCertificates(getAssets().open("aaa.cer"),
                                    getAssets().open("server.cer"));
                } catch (IOException e)
                {
                    e.printStackTrace();
                }

        即可。别忘了注册Application。

        */

    }
}
