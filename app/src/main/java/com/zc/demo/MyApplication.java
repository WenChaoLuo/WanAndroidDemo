package com.zc.demo;

import android.app.Application;

import com.zc.utillibrary.RxToast;

/**
 * @author wenchao
 * @version 1.0.1
 * @className MyApplication
 * @date 2019/7/30
 * @description
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        RxToast.init(this);
    }
}
