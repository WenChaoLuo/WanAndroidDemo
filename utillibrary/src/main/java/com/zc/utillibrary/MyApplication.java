package com.zc.utillibrary;

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;

import java.lang.ref.WeakReference;

/**
 * @author wenchao
 * @version 1.0.1
 * @className MyApplication
 * @date 2019/8/1
 * @description
 */
public class MyApplication extends Application {
    private Typeface iconfont;
    private static MyApplication instance = null;
    private static WeakReference<MyApplication> reference = null;

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.logNormalMsg("librart的oncreate执行了-------------------------------");
    }

    public static MyApplication getInstance() {
        if (instance == null) {
            synchronized (MyApplication.class) {
                instance = new MyApplication();
                reference = new WeakReference<>(instance);
            }
        }
        return reference.get();
    }
    /**
     * 初始化加载Iconfont
     * @param context Context
     * @return Typeface
     */
    public Typeface getIconfont(Context context) {
        if (iconfont != null) {
            return iconfont;
        } else {
            Typeface typeface = Typeface.createFromAsset(context.getApplicationContext().getAssets(), "iconfont.ttf");
            WeakReference<Typeface> typefaceWeakReference = new WeakReference<>(typeface);
            iconfont = typefaceWeakReference.get();
        }
        return iconfont;
    }
}
