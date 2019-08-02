package com.zc.utillibrary.customview;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import com.zc.utillibrary.MyApplication;
/**
 * @author wenchao
 * @date  2019/7/18
 * @version 1.0.1
 * @description 显示svg图片的TextView
 */
public class TextViewIcon extends AppCompatTextView {
    public TextViewIcon(Context context) {
        super(context);
        init(context);
    }

    public TextViewIcon(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TextViewIcon(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        //加载iconfont
        setTypeface(MyApplication.getInstance().getIconfont(context));
    }

}