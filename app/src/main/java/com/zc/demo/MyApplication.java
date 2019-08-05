package com.zc.demo;

import android.app.Activity;
import android.app.Application;
import android.support.v4.app.FragmentActivity;

import com.zc.utillibrary.RxToast;

import java.util.List;

/**
 * @author wenchao
 * @version 1.0.1
 * @className MyApplication
 * @date 2019/7/30
 * @description
 */
public class MyApplication extends Application {
    private List<Activity> list ;
    @Override
    public void onCreate() {
        super.onCreate();
        RxToast.init(this);
    }

}
