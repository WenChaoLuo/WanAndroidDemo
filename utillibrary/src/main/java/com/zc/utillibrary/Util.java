package com.zc.utillibrary;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityOptions;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.TimeUtils;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author wenchao
 * @date  2019/7/18
 * @version 1.0.1
 * @description 常用工具类
 */
public class Util {
    /**邮箱正则表达式*/
    private static final String emailReg = "^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\\.([a-zA-Z0-9_-])+)+$";
    private static float sNoncompatDensity;
    private static float sNoncompatScaledDensity;
    public static boolean matchEmail(String email){
        return !RegexUtils.isMatch(emailReg,email);
    }

    /**
     * 设置字体适配
     * @param activity Activity
     * @param application Application
     */
    public static void setCustomDensity(Activity activity, final Application application){
        final DisplayMetrics displayMetrics = application.getResources().getDisplayMetrics();
        if (sNoncompatDensity == 0) {
            sNoncompatDensity = displayMetrics.density;
            sNoncompatScaledDensity = displayMetrics.scaledDensity;
            application.registerComponentCallbacks(new ComponentCallbacks() {
                @Override
                public void onConfigurationChanged(Configuration newConfig) {
                    if (newConfig != null && newConfig.fontScale > 0) {
                        sNoncompatScaledDensity = application.getResources().getDisplayMetrics().scaledDensity;
                    }
                }

                @Override
                public void onLowMemory() {

                }
            });
        }
        final  float targetDensity = displayMetrics.widthPixels / 360;
        final  float targetScaledDensity = targetDensity * ( sNoncompatScaledDensity / sNoncompatDensity);
        final int targetDensityDpi = (int)(160 * targetDensity);

        displayMetrics.density = targetDensity;
        displayMetrics.scaledDensity = targetScaledDensity;
        displayMetrics.densityDpi = targetDensityDpi;
        final DisplayMetrics activityDisplayMetrics = activity.getResources().getDisplayMetrics();
        activityDisplayMetrics.density = targetDensity;
        activityDisplayMetrics.scaledDensity = targetScaledDensity;
        activityDisplayMetrics.densityDpi = targetDensityDpi;
    }
    /**
     * 获取当前本地apk的版本
     *
     * @param mContext the m context
     * @return int version code
     */
    public static int getVersionCode(Context mContext) {
        int versionCode = 0;
        try {
            //获取软件版本号，对应AndroidManifest.xml下android:versionCode
            versionCode = mContext.getPackageManager().
                    getPackageInfo(mContext.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * 获取版本号名称
     *
     * @param context 上下文
     * @return String version name
     */
    public static String getVersionName(Context context) {
        String verName = "";
        try {
            verName = context.getPackageManager().
                    getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return verName;
    }

    /**
     * Gets online shader.
     *
     * @return the online shader
     */
    public static Shader getOnlineShader() {
        Shader shader = new LinearGradient(0, 0, ConvertUtils.dp2px(40), ConvertUtils.dp2px(40), Color.parseColor("#29f3d9"), Color.parseColor("#27a2e1"), Shader.TileMode.CLAMP);
        return shader;
    }

    /**
     * 判断点击事件是否发生在当前View中
     *
     * @param view View
     * @param ev   事件
     * @return boolean boolean
     */
    public static boolean inRangeOfView(View view, MotionEvent ev) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int x = location[0];
        int y = location[1];
        if (ev.getX() < x || ev.getX() > (x + view.getWidth()) || ev.getY() < y || ev.getY() > (y + view.getHeight())) {
            return false;
        }
        return true;
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     *
     * @param v     the v
     * @param event the event
     * @return boolean
     */
    public static void isShouldHideKeyboard(View v, MotionEvent event) {
        //判断得到的焦点控件是否包含EditText
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            //得到输入框在屏幕中上下左右的位置
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            boolean flag = (event.getX() > left && event.getX() < right && event.getY() > top && event.getY() < bottom);
            if (flag) {
                // 点击位置如果是EditText的区域，忽略它，不收起键盘。
            } else {
                KeyboardUtils.hideSoftInput(v);
            }
        }
        // 如果焦点不是EditText则忽略
    }

    /**
     * 判断设置数据
     *
     * @param textView TextView
     * @param data     数据
     */
    public static void judgeSetDataEmpty(TextView textView, String data) {
        if (TextUtils.isEmpty(data)) {
            textView.setText("无");
        } else {
            textView.setText(data);
        }
    }
    /**
     * 判断设置数据
     *
     * @param textView TextView
     * @param data     数据
     */
    public static void judgeSetDataUnknown(TextView textView, String data) {
        if (TextUtils.isEmpty(data)) {
            textView.setText("未知");
        } else {
            textView.setText(data);
        }
    }

    /**
     * 是否显示View
     *
     * @param view   View
     * @param isShow 是否
     */
    public static void showView(View view, boolean isShow) {
        if (view != null) {
            if (isShow) {
                view.setVisibility(View.VISIBLE);
            } else {
                view.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 判断是否是空的list
     *
     * @param data 数据
     * @return boolean boolean
     */
    public static boolean isEmptyList(List data) {
        return data == null || data.size() < 1;
    }

    /**
     *
     * bytes转换成十六进制字符串
     *
     * @param b 数据
     * @return String 每个Byte值之间空格分隔
     */
    public static String byte2HexStr(byte[] b) {
        String stmp;
        StringBuilder sb = new StringBuilder("");
        for (byte b1 : b) {
            stmp = Integer.toHexString(b1 & 0xFF);
            sb.append((stmp.length() == 1) ? "0" + stmp : stmp);
            sb.append("");
        }
        return sb.toString().toUpperCase().trim();
    }

    /**
     * byte转ASCII
     *
     * @param bytes   the bytes
     * @param offset  the offset
     * @param dateLen the date len
     * @return String string
     */
    private static String bytesToAscii(byte[] bytes, int offset, int dateLen) {
        if ((bytes == null) || (bytes.length == 0) || (offset < 0) || (dateLen <= 0)) {
            return null;
        }
        if ((offset >= bytes.length) || (bytes.length - offset < dateLen)) {
            return null;
        }

        String asciiStr = null;
        byte[] data = new byte[dateLen];
        System.arraycopy(bytes, offset, data, 0, dateLen);
        try {
            asciiStr = new String(data, "ISO8859-1");
        } catch (UnsupportedEncodingException e) {
        }
        return asciiStr;
    }

    /**
     * Bytes to ascii string.
     *
     * @param bytes   the bytes
     * @param dateLen the date len
     * @return the string
     */
    public static String bytesToAscii(byte[] bytes, int dateLen) {
        return bytesToAscii(bytes, 0, dateLen);
    }

    /**
     * Bytes to ascii string.
     *
     * @param bytes the bytes
     * @return the string
     */
    public static String bytesToAscii(byte[] bytes) {
        return bytesToAscii(bytes, 0, bytes.length);
    }

    /**
     * String to ascii string.
     *
     * @param value the value
     * @return the string
     */
    public static String stringToAscii(String value) {
        StringBuffer sbu = new StringBuffer();
        char[] chars = value.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (i != chars.length - 1) {
                sbu.append((int) chars[i]).append(",");
            } else {
                sbu.append((int) chars[i]);
            }
        }
        return sbu.toString();
    }

    /**
     * Get hex bytes byte [ ].
     *
     * @param message the message
     * @return the byte [ ]
     */
    public static byte[] getHexBytes(String message) {
        int len = message.length() / 2;
        char[] chars = message.toCharArray();
        String[] hexStr = new String[len];
        byte[] bytes = new byte[len];
        for (int i = 0, j = 0; j < len; i += 2, j++) {
            hexStr[j] = "" + chars[i] + chars[i + 1];
            bytes[j] = (byte) Integer.parseInt(hexStr[j], 16);
        }
        return bytes;
    }


    /**
     * 比较版本号
     * 格式：1.0.1
     * @param localVersion 本地版本号
     * @param serverVersion 服务器版本号
     * @return boolean
     */
    public static boolean isHasNewVersion(String localVersion, String serverVersion) {
        boolean flag = false;
        try {
            String[] locaStr = localVersion.split("\\.");
            String[] serverStr = serverVersion.split("\\.");
            for (int i = 0; i < locaStr.length; i++) {
                int v = Integer.parseInt(locaStr[i]);
                int vv = Integer.parseInt(serverStr[i]);
                if (vv > v) {
                    flag = true;
                    break;
                }
            }
        } catch (Exception e) {
            Log.e("wenchao", "比对版本号时出错：" + e.getMessage());
        }

        return flag;
    }

    /**
     * 保存头像bitmap
     * @param bitmap Bitmap
     * @param imgPath 图片保存路径
     * @return String 保存路径
     */
    public static String saveAvatarBitmap(Bitmap bitmap, String imgPath, String avatarPath){
        String path = null;
        FileUtils.deleteFilesInDir(avatarPath);
        if (imgPath.endsWith("jpg")){
             path = avatarPath+File.separator+TimeUtils.getNowMills()+"avatar.jpg";
        }else {
             path = avatarPath+File.separator+TimeUtils.getNowMills()+"avatar.png";
        }
        File f = new File(path);
        if (f.exists()) {
            f.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
            out.close();
        }  catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            path = "error";
        }
        return path;
    }

    /**
     * 保存异常信息到文件中
     * @param ex 异常信息
     * @param context Context
     * @param logPath 日志保存路径
     */
    public static void handleException(Throwable ex, Context context, String logPath) {
        // 导出异常信息到SD卡中
        try {
            dumpExceptionToSdCard(ex,context,logPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 写入日志
     *
     * @param ex 异常信息
     * @throws IOException
     */
    @SuppressLint("SimpleDateFormat")
    private static void dumpExceptionToSdCard(Throwable ex, Context context, String logPath) throws IOException {
        WeakReference<Context> reference = new WeakReference<>(context);
        Handler handler = new Handler(reference.get().getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                RxToast.showBottom("程序出现异常");
            }
        });
        File dir = new File(logPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        long current = System.currentTimeMillis();
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                .format(new Date
                        (current));
        // 以当前时间创建log文件
        File file = new File(logPath +File.separator+ "crash-" + time
                + ".txt");
        try {
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(
                    file)));
            // 导出发生异常的时间
            pw.println(time);
            // 导出手机信息
            dumpPhoneInfo(pw,context);
            pw.println();
            // 导出异常的调用栈信息
            ex.printStackTrace(pw);
            pw.close();
        } catch (Exception e) {
            LogUtil.logNormalMsg("dump crash info failed");
        }
    }

    /**
     * 获取手机信息
     * @param pw PrintWriter
     * @param context Context
     * @throws PackageManager.NameNotFoundException
     */
    private static void dumpPhoneInfo(PrintWriter pw,Context context) throws PackageManager.NameNotFoundException {
        // 应用的版本名称和版本号
        PackageManager pm = context.getPackageManager();
        PackageInfo pi = pm.getPackageInfo(context.getPackageName(),
                PackageManager.GET_ACTIVITIES);
        pw.print("App Version: ");
        pw.print(pi.versionName);
        pw.print('_');
        pw.println(pi.versionCode);
        // android版本号
        pw.print("OS Version: ");
        pw.print(Build.VERSION.RELEASE);
        pw.print("_");
        pw.println(Build.VERSION.SDK_INT);
        // 手机制造商
        pw.print("Vendor: ");
        pw.println(Build.MANUFACTURER);
        // 手机型号
        pw.print("Model: ");
        pw.println(Build.MODEL);
        // cpu架构
        pw.print("CPU ABI: ");
        pw.println(Build.CPU_ABI);
    }

    /**
     * 获取activity调转的Bundle
     * @param activity  Activity
     * @return Bundle
     */
    public static Bundle getActivityChangeBundle(Activity activity){
        WeakReference<Activity> reference = new WeakReference<>(activity);
        return ActivityOptions.makeSceneTransitionAnimation(reference.get()).toBundle();
    }

}
