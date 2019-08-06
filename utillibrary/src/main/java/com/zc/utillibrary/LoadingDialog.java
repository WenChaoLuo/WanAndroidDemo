package com.zc.utillibrary;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ConvertUtils;


/**
 * @author wenchao
 * @date  2019/7/18
 * @version 1.0.1
 * @description 加载提示dialog
 */
public class LoadingDialog implements Animation.AnimationListener{
    private AlertDialog dialog;
    public void show(Context context, String title){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        View view = LayoutInflater.from(context).inflate(R.layout.loading_dialog_layout, null);
        TextView textView = view.findViewById(R.id.loading_dialog_title_tv);
        ImageView imageView = view.findViewById(R.id.progress_view);

        RotateAnimation animation =  new RotateAnimation(0,360*74, Animation.RELATIVE_TO_SELF,
                0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        animation.setDuration(75000);
        animation.setRepeatCount(1);
        animation.setAnimationListener(this);
        animation.setFillAfter(true);
        animation.setInterpolator(new LinearInterpolator());
        imageView.startAnimation(animation);
        if (!TextUtils.isEmpty(title)){
            textView.setText(title);
        }
        builder.setView(view);
        dialog = builder.create();
        dialog.show();
        Window window = dialog.getWindow();
        //去除背景遮罩
        window.setDimAmount(0f);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.CENTER;
        lp.width = ConvertUtils.dp2px(90);
        lp.height = ConvertUtils.dp2px(90);
        window.setAttributes(lp);

    }
    public void dismiss(){
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;
        }
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
