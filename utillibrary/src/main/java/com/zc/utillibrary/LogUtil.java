package com.zc.utillibrary;

import android.util.Log;

/**
 * @author wenchao
 * @date  2019/7/18
 * @version 1.0.1
 * @description log工具类
 */
public class LogUtil {
    private static String TAG = "wenchao";
    /**
     * 打印日志
     * @param msg 内容
     */
    public static void logNormalMsg(String msg){
        if (BuildConfig.DEBUG) {
            Log.e(TAG, msg);
        }
//        String filePath = Path.logPath+ File.separator+"Log.txt";
//        FileUtils.createOrExistsFile(filePath);
//        String content = TimeUtils.getNowString()+"："+msg+"\n\n\n";
//        FileIOUtils.writeFileFromString(filePath,content,true);
    }
    /**
     * 打印日志
     * @param msg 内容
     */
    public static void logNormalMsg(String tag,String msg){
        if (BuildConfig.DEBUG) {
            Log.e(tag, msg);
        }
//        String filePath = Path.logPath+ File.separator+"Log.txt";
//        FileUtils.createOrExistsFile(filePath);
//        String content = TimeUtils.getNowString()+"："+msg+"\n\n\n";
//        FileIOUtils.writeFileFromString(filePath,content,true);
    }
}
