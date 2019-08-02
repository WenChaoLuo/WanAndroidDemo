//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.zc.utillibrary;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author wenchao
 * @date  2019/7/18
 * @version 1.0.1
 * @description 权限工具类
 */
public class RxPermissionsTool {
    public static final int PERMISSION_REQUEST_CODE = 55;
    public RxPermissionsTool() {
    }

    public static RxPermissionsTool.Builder with(Activity activity) {
        return new RxPermissionsTool.Builder(activity);
    }

    public static class Builder {
        private Activity mActivity;
        private List<String> permissionList;

        public Builder(@NonNull Activity activity) {
            this.mActivity = activity;
            this.permissionList = new ArrayList();
        }

        /**
         * 添加权限
         * @param permission 权限
         * @return RxPermissionsTool.Builder
         */
        public RxPermissionsTool.Builder addPermission(@NonNull String permission) {
            if (!this.permissionList.contains(permission)) {
                this.permissionList.add(permission);
            }
            return this;
        }

        /**
         * 检查并请求权限
         * @return List<String>
         */
        public List<String> initPermission() {
            List<String> list = new ArrayList();
            Iterator var2 = this.permissionList.iterator();

            while(var2.hasNext()) {
                String permission = (String)var2.next();
                if (ActivityCompat.checkSelfPermission(this.mActivity, permission) != 0) {
                    list.add(permission);
                }
            }

            if (list.size() > 0) {
                ActivityCompat.requestPermissions(this.mActivity, (String[])list.toArray(new String[list.size()]), PERMISSION_REQUEST_CODE);
            }

            return list;
        }

    }

    /**
     * 检查权限
     *
     * @param permission 权限
     * @param context    context
     * @return boolean boolean
     */
    public static boolean checkPermission(String permission, Context context){
        if (ActivityCompat.checkSelfPermission(context,permission)!= PackageManager.PERMISSION_GRANTED) {
            return false;
        }else {
            return true;
        }
    }
}
