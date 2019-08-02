package com.zc.utillibrary;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.ConvertUtils;

import java.lang.ref.WeakReference;


/**
 * @author wenchao
 * @date  2019/7/18
 * @version 1.0.1
 * @description Toast工具类
 */
public class RxToast {
    private static Toast toast;
    private static WeakReference<Context> weakReference;

    /**
     * 构造方法初始化
     * 请在Application中调用此方法
     * @param c Context
     */
    public static void init(Context c){
        weakReference = new WeakReference<>(c);
    }

    /**
     * Success.
     *  成功弹框
     * @param msg the msg 内容
     */
    public static void success(String msg){
        if (weakReference.get() == null) {
            return;
        }
        View view = LayoutInflater.from(weakReference.get()).inflate(R.layout.success_toast_layout,null);
        TextView textView = view.findViewById(R.id.tips_msg_tv);
        if (!TextUtils.isEmpty(msg)) {
            textView.setText(msg);
        }
        if (toast == null) {
            toast = new Toast(weakReference.get());
        }
        toast.setGravity(Gravity.BOTTOM,0, ConvertUtils.dp2px(40));
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
        toast.show();
    }
    /**
     *  底部Toast
     * @param msg the msg 内容
     */
    public static void showBottom(String msg){
        if (weakReference.get() == null) {
            return;
        }
        View view = LayoutInflater.from(weakReference.get()).inflate(R.layout.tips_toast_layout,null);
        TextView textView = view.findViewById(R.id.tips_msg_tv);
        if (!TextUtils.isEmpty(msg)) {
            textView.setText(msg);
        }
        if (toast == null) {
            toast = new Toast(weakReference.get());
        }
        toast.setGravity(Gravity.BOTTOM,0, ConvertUtils.dp2px(40));
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
        toast.show();
    }
    /**
     * Error.
     *  错误弹框
     * @param msg the msg 内容
     */
    public static void error(String msg){
        if (weakReference.get() == null) {
            return;
        }
        View view = LayoutInflater.from(weakReference.get()).inflate(R.layout.tips_toast_layout,null);
        TextView textView = view.findViewById(R.id.tips_msg_tv);
        if (!TextUtils.isEmpty(msg)) {
            textView.setText(msg);
        }
        if (toast == null) {
            toast = new Toast(weakReference.get());
        }
        toast.setGravity(Gravity.CENTER,0,0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
        toast.show();
    }

    /**
     * Warning.
     *  警告弹框
     * @param msg the msg 内容
     */
    public static void warning(String msg){
        if (weakReference.get() == null) {
            return;
        }
        View view = LayoutInflater.from(weakReference.get()).inflate(R.layout.tips_toast_layout,null);
        TextView textView = view.findViewById(R.id.tips_msg_tv);
        if (!TextUtils.isEmpty(msg)) {
            textView.setText(msg);
        }
        if (toast == null) {
            toast = new Toast(weakReference.get());
        }
        toast.setGravity(Gravity.CENTER,0,0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
        toast.show();
    }

    /**
     * Show tips.
     *  提示弹框
     * @param msg the msg 内容
     */
    public static void showTips(String msg){
        if (weakReference.get() == null) {
            return;
        }
        View view = LayoutInflater.from(weakReference.get()).inflate(R.layout.tips_toast_layout,null);
        TextView textView = view.findViewById(R.id.tips_msg_tv);
        if (!TextUtils.isEmpty(msg)) {
            textView.setText(msg);
        }
        if (toast == null) {
            toast = new Toast(weakReference.get());
        }
        toast.setGravity(Gravity.CENTER,0,0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
        toast.show();
    }
}
