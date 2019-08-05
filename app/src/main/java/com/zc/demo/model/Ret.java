package com.zc.demo.model;

import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * @author wenchao
 * @version 1.0.1
 * @date 2019/7/16
 */
public class Ret {
    /**
     * 判断View是否可见
     *
     * @param target   View
     * @param judgeAll 为true时,判断View全部可见才返回true
     * @return boolean
     */
    public static boolean isVisibleLocal(View target, boolean judgeAll) {
        Rect rect = new Rect();
        target.getLocalVisibleRect(rect);
        if (judgeAll) {
            return rect.top == 0;
        }else {
            return rect.top >= 0;
        }
    }

}
