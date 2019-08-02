//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.zc.utillibrary;

import android.content.Context;
import android.view.WindowManager;

import java.lang.ref.WeakReference;

/**
 * @author wenchao
 * @date  2019/7/18
 * @version 1.0.1
 * @description device工具类
 */
public class RxDeviceTool {
    public RxDeviceTool() {
    }

    public static int getScreenHeight(Context context) {
        WeakReference<Context> reference = new WeakReference<>(context);
        WindowManager wm = (WindowManager)reference.get().getSystemService("window");
        int height = wm.getDefaultDisplay().getHeight();
        return height;
    }

    public static int getScreenWidth(Context context) {
        WeakReference<Context> reference = new WeakReference<>(context);
        WindowManager wm = (WindowManager)reference.get().getSystemService("window");
        int width = wm.getDefaultDisplay().getWidth();
        return width;
    }

}
