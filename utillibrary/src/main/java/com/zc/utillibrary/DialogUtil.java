package com.zc.utillibrary;

import android.app.KeyguardManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.os.Handler;
import android.support.v4.util.SimpleArrayMap;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.weigan.loopview.LoopView;
import com.weigan.loopview.OnItemSelectedListener;
import com.zc.utillibrary.customview.TextViewIcon;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wenchao
 * @date  2019/7/18
 * @version 1.0.1
 * @description 弹框工具类
 */
public class DialogUtil {
    private  AlertDialog dialog;
    private  int time;
    private  int month;
    private  int year;
    private  LoopView yearLoopView;
    private  LoopView monthLoopView;

    private static  DialogUtil instance;
    private FingerprintManager fingerprintManager;
    private CancellationSignal cancellationSignal;

    private DialogUtil() {
    }

    /**
     * Get instance dialog util.
     *
     * @return the dialog util
     */
    public static DialogUtil getInstance(){
        if (instance == null){
            synchronized (DialogUtil.class){
                if (instance == null){
                    instance = new DialogUtil();
                }
            }
        }
        return instance;
    }

    /**
     * 喷淋时间选择框
     *
     * @param context  Context
     * @param listener 监听器
     */
    public  void showTimeSelectDialog(Context context, final DialogConfirmListener<Integer> listener){
        WeakReference<Context> reference = new WeakReference<>(context);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        View view = LayoutInflater.from(reference.get()).inflate(R.layout.select_time_layout, null);
        TextViewIcon cancel_btn = view.findViewById(R.id.id_select_month_delete_icon);
        TextView confirm_btn = view.findViewById(R.id.id_select_month_confirm_tv);

        LoopView timeLoopView = view.findViewById(R.id.id_select_time_loop_view);
        final List<String> items = new ArrayList<>();

        for (int i = 1; i < 6; i++) {
            items.add((10*i)+"");
        }
        timeLoopView.setItems(items);
        timeLoopView.setCenterTextColor(Color.parseColor("#333333"));
        timeLoopView.setDividerColor(Color.parseColor("#caf3f3"));
        timeLoopView.setInitPosition(0);
        time = Integer.parseInt(items.get(0));
        timeLoopView.setOuterTextColor(reference.get().getResources().getColor(R.color.text_color_gray));
        timeLoopView.setTextSize(18);
        timeLoopView.setLineSpacingMultiplier(2f);
        timeLoopView.setItemsVisibleCount(7);
        timeLoopView.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                String t = items.get(index);
                time = Integer.parseInt(t);
            }
        });

        builder.setView(view);
        dialog = builder.create();
        dialog.show();

        Window window = dialog.getWindow();
        assert window != null;
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.gravity = Gravity.CENTER;
        lp.width = ConvertUtils.dp2px(344);
        lp.height = ConvertUtils.dp2px(254);
        dialog.getWindow().setAttributes(lp);

        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.cancel();
                }
                dialog.dismiss();
                dialog.cancel();
                dialog = null;
                monthLoopView.cancelFuture();
                monthLoopView = null;
                yearLoopView.cancelFuture();
                yearLoopView = null;
            }
        });
        confirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.confirm(time);
                }
                dialog.dismiss();
                dialog.cancel();
                dialog = null;
            }
        });
    }

    /**
     * 月份选择框
     *
     * @param context  context
     * @param listener 监听器
     */
    public  void showMonthSelectDialog(Context context, final DialogConfirmListener<SimpleArrayMap<Integer,Integer>> listener){
        WeakReference<Context> reference = new WeakReference<>(context);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        final int lastMonth = Integer.parseInt(TimeUtils.getNowString(new SimpleDateFormat("MM")))-1;
        View view = LayoutInflater.from(reference.get()).inflate(R.layout.select_month_layout, null);
        TextViewIcon cancelBtn = view.findViewById(R.id.id_select_month_delete_icon);
        TextView confirmBtn = view.findViewById(R.id.id_select_month_confirm_tv);
        yearLoopView = view.findViewById(R.id.id_select_month_year_loop_view);
        monthLoopView = view.findViewById(R.id.id_select_month_loop_view);
        final List<String> items = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            items.add(""+(2015+i));
        }
        final List<String> items2 = new ArrayList<>();
        for (int i = 0; i <= lastMonth; i++) {
            if (i<9){
                items2.add("0"+(1+i));
            }else {
                items2.add((1+i)+"");
            }
        }
        yearLoopView.setItems(items);
        monthLoopView.setItems(items2);
        yearLoopView.setCenterTextColor(Color.parseColor("#333333"));
        yearLoopView.setDividerColor(Color.parseColor("#caf3f3"));
        yearLoopView.setInitPosition(items.size()-1);
        year = Integer.parseInt(items.get(items.size()-1));
        yearLoopView.setOuterTextColor(context.getResources().getColor(R.color.text_color_gray));
        yearLoopView.setTextSize(18);
        yearLoopView.setLineSpacingMultiplier(2f);
        yearLoopView.setItemsVisibleCount(7);
        yearLoopView.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                String t = items.get(index);
                year = Integer.parseInt(t);
                if (index == (items.size()-1)){
                    items2.clear();
                    for (int i = 0; i < lastMonth; i++) {
                        if (i<9){
                            items2.add("0"+(1+i));
                        }else {
                            items2.add((1+i)+"");
                        }
                    }
                    monthLoopView.setItems(items2);
                    monthLoopView.setInitPosition(items2.size()-1);
                    month = Integer.parseInt(items2.get(items2.size()-1));
                }else {
                    items2.clear();
                    for (int i = 0; i < 12; i++) {
                        if (i<9){
                            items2.add("0"+(1+i));
                        }else {
                            items2.add((1+i)+"");
                        }
                    }
                    monthLoopView.setItems(items2);
                    monthLoopView.setInitPosition(items2.size()-1);
                    month = Integer.parseInt(items2.get(items2.size()-1));
                }

            }
        });

        monthLoopView.setCenterTextColor(Color.parseColor("#333333"));
        monthLoopView.setDividerColor(Color.parseColor("#caf3f3"));
        monthLoopView.setInitPosition(items2.size()-1);
        month = Integer.parseInt(items2.get(items2.size()-1));
        monthLoopView.setOuterTextColor(context.getResources().getColor(R.color.text_color_gray));
        monthLoopView.setTextSize(18);
        monthLoopView.setLineSpacingMultiplier(2f);
        monthLoopView.setItemsVisibleCount(7);
        monthLoopView.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                String t = items2.get(index);
                month = Integer.parseInt(t);
            }
        });
        builder.setView(view);
        dialog = builder.create();
        dialog.show();
        Window window = dialog.getWindow();
        assert window != null;
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.gravity = Gravity.CENTER;
        lp.width = ConvertUtils.dp2px(344);
        lp.height = ConvertUtils.dp2px(254);
        dialog.getWindow().setAttributes(lp);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.cancel();
                }
                dialog.dismiss();
                dialog.cancel();
                dialog = null;
                monthLoopView.cancelFuture();
                monthLoopView.clearAnimation();
                monthLoopView = null;
                yearLoopView.cancelFuture();
                yearLoopView = null;
            }
        });
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    SimpleArrayMap<Integer,Integer> hashMap = new SimpleArrayMap<>(1);
                    hashMap.put(year,month);
                    listener.confirm(hashMap);
                }
                dialog.dismiss();
                dialog.cancel();
                dialog = null;
                monthLoopView.cancelFuture();
                monthLoopView.clearAnimation();
                monthLoopView = null;
                yearLoopView.cancelFuture();
                yearLoopView = null;
            }
        });
    }

    /**
     * 显示默认提示确认框（退出时提示）
     * @param context context
     * @param listener 回调
     */
    public  void showConfirmDialogDefault(Context context, final DialogConfirmListener listener){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        View view = LayoutInflater.from(context).inflate(R.layout.confirm_alertdialog_layout, null);
        TextView cancelBtn = view.findViewById(R.id.id_confirm_cancel_tv);
        TextView confirmBtn = view.findViewById(R.id.id_confirm_confirm_tv);

        builder.setView(view);
        dialog = builder.create();
        dialog.show();

        Window window = dialog.getWindow();
        assert window != null;
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.gravity = Gravity.CENTER;
        lp.width = ConvertUtils.dp2px(290);
        lp.height = ConvertUtils.dp2px(160);
        dialog.getWindow().setAttributes(lp);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.cancel();
                }
                dialog.dismiss();
                dialog.cancel();
                dialog = null;
            }
        });
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.confirm(null);
                }
                dialog.dismiss();
                dialog.cancel();
                dialog = null;
            }
        });
    }

    /**
     * 显示忘记密码弹框
     * @param context context
     * @param listener 回调
     */
    public  void showForgetPasswordDialog(Context context, final DialogForgetPwdListener listener){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        WeakReference<Context> reference = new WeakReference<>(context);
        View view = LayoutInflater.from(reference.get()).inflate(R.layout.forget_password_dialog_layout, null);
        TextView cancelBtn = view.findViewById(R.id.find_pwd_cancel_tv);
        TextView findPwdByPhoneTv = view.findViewById(R.id.find_pwd_by_phone_tv);
        TextView findPwdByEmailTv = view.findViewById(R.id.find_pwd_by_email_tv);

        builder.setView(view);
        dialog = builder.create();
        dialog.show();

        Window window = dialog.getWindow();
        assert window != null;
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.gravity = Gravity.CENTER;
        lp.width = ConvertUtils.dp2px(315);
        lp.height = ConvertUtils.dp2px(245);
        dialog.getWindow().setAttributes(lp);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        findPwdByEmailTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                dialog.cancel();
                dialog = null;
                if (listener != null) {
                    listener.findPwdByEmail();
                }
            }
        });
        findPwdByPhoneTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                dialog.cancel();
                dialog = null;
                if (listener != null) {
                    listener.findPwdByPhone();
                }

            }
        });
    }

    /**
     * 显示消息弹框
     * @param context Context
     * @param msg 消息内容
     * @param isSuccess 成功或者失败标志
     */
    public  void showMsgDialog(Context context, String msg,boolean isSuccess){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        WeakReference<Context> reference = new WeakReference<>(context);
        View view = LayoutInflater.from(reference.get()).inflate(R.layout.show_msg_dialog_layout, null);
        ImageView msgImg = view.findViewById(R.id.show_msg_img);
        TextView msgTv = view.findViewById(R.id.show_msg_tv);
        if (!TextUtils.isEmpty(msg)) {
            msgTv.setText(msg);
        }
        if (isSuccess) {
            msgImg.setImageResource(R.drawable.ic_success_icon);
        }else {
            msgImg.setImageResource(R.drawable.ic_fail_icon);
        }
        builder.setView(view);
        dialog = builder.create();
        dialog.show();

        Window window = dialog.getWindow();
        assert window != null;
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.gravity = Gravity.CENTER;
        lp.width = ConvertUtils.dp2px(315);
        lp.height = ConvertUtils.dp2px(116);
        dialog.getWindow().setAttributes(lp);
        Handler handler = new Handler(context.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
                dialog.cancel();
                dialog = null;
            }
        },1900);
    }
    /**
     * 自定义提示确认框
     *
     * @param context         context
     * @param title           标题
     * @param info            提示信息
     * @param confirmBtnTitle 确认按钮文字
     * @param cancelBtnTitle  退出按钮文字
     * @param listener        回调
     */
    public  void showConfirmDialog(Context context, String title, String info, String confirmBtnTitle,
                                         String cancelBtnTitle, final DialogConfirmListener listener){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        WeakReference<Context> reference = new WeakReference<>(context);
        View view = LayoutInflater.from(reference.get()).inflate(R.layout.confirm_alertdialog_layout, null);
        TextView titleTv = view.findViewById(R.id.id_confirm_title_tv);
        TextView infoTv = view.findViewById(R.id.id_confirm_msg_tv);
        RelativeLayout cancelLayout = view.findViewById(R.id.id_confirm_cancel_layout);
        RelativeLayout confirmLayout = view.findViewById(R.id.id_confirm_confirm_layout);
        TextView cancelBtn = view.findViewById(R.id.id_confirm_cancel_tv);
        TextView confirmBtn = view.findViewById(R.id.id_confirm_confirm_tv);
        if (!TextUtils.isEmpty(title)) {
            titleTv.setText(title);
        }
        if (!TextUtils.isEmpty(info)) {
            infoTv.setText(info);
        }if (!TextUtils.isEmpty(confirmBtnTitle)) {
            confirmBtn.setText(confirmBtnTitle);
        }else {
            Util.showView(confirmLayout,false);
        }
        if (!TextUtils.isEmpty(cancelBtnTitle)) {
            cancelBtn.setText(cancelBtnTitle);
        }else {
            Util.showView(cancelLayout,false);
        }
        builder.setView(view);
        dialog = builder.create();
        dialog.show();
        Window window = dialog.getWindow();
        assert window != null;
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.gravity = Gravity.CENTER;
        lp.width = ConvertUtils.dp2px(290);
        lp.height = ConvertUtils.dp2px(160);
        dialog.getWindow().setAttributes(lp);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.cancel();
                }
                dialog.dismiss();
                dialog.cancel();
                dialog = null;
            }
        });
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.confirm(null);
                }
                dialog.dismiss();
                dialog.cancel();
                dialog = null;
            }
        });
    }

    /**
     * 自定义确认弹框
     * @param context Context
     * @param title 标题
     * @param info 信息
     * @param confirmBtnTitle 确认按钮文字
     */
    public  void showConfirmDialog(Context context, String title, String info, String confirmBtnTitle){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        WeakReference<Context> reference = new WeakReference<>(context);
        View view = LayoutInflater.from(reference.get()).inflate(R.layout.confirm_alert_dialog_layout, null);
        TextView titleTv = view.findViewById(R.id.id_confirm_title_tv);
        TextView infoTv = view.findViewById(R.id.id_confirm_msg_tv);
        TextView confirmBtn = view.findViewById(R.id.id_confirm_confirm_tv);
        if (!TextUtils.isEmpty(title)) {
            titleTv.setText(title);
        }
        if (!TextUtils.isEmpty(info)) {
            infoTv.setText(info);
        }if (!TextUtils.isEmpty(confirmBtnTitle)) {
            confirmBtn.setText(confirmBtnTitle);
        }
        builder.setView(view);
        dialog = builder.create();
        dialog.show();
        Window window = dialog.getWindow();
        assert window != null;
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.gravity = Gravity.CENTER;
        lp.width = ConvertUtils.dp2px(290);
        lp.height = ConvertUtils.dp2px(160);
        dialog.getWindow().setAttributes(lp);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                dialog.cancel();
                dialog = null;
            }
        });
    }

    /**
     * 指纹设置弹框
     * @param context Context
     * @param listener  listener
     */
    public void showFingerprintConfirmDialog(Context context, final DialogFingerprintListener listener){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        WeakReference<Context> reference = new WeakReference<>(context);
        View view = LayoutInflater.from(reference.get()).inflate(R.layout.figerpring_verification_layout, null);
        TextView cancelBtn = view.findViewById(R.id.id_confirm_cancel_tv);
        builder.setView(view);
        dialog = builder.create();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            RxToast.showTips("您的系统版本过低，不支持指纹功能");
        }else {
            if (supportFingerprint(reference.get())){
                if (fingerprintManager == null){
                    fingerprintManager = reference.get().getSystemService(FingerprintManager.class);
                }
                if (cancellationSignal == null) {
                    cancellationSignal = new CancellationSignal();
                }
                fingerprintManager.authenticate(null, cancellationSignal, 0, new FingerprintManager.AuthenticationCallback() {
                    @Override
                    public void onAuthenticationError(int errorCode, CharSequence errString) {
                        super.onAuthenticationError(errorCode, errString);
                        Log.e("DialogUtil", "errorCode:5" + errorCode);
                        LogUtil.logNormalMsg("尝试次数过多，请稍后重试");

                        if (listener != null && errorCode != 5) {
                            listener.verificationLater();
                        }
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                            dialog.cancel();
                            dialog = null;
                            cancellationSignal.cancel();
                            cancellationSignal = null;
                            fingerprintManager = null;
                        }
                    }

                    @Override
                    public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
                        super.onAuthenticationHelp(helpCode, helpString);
                        LogUtil.logNormalMsg("指纹验证失败，可再验，可能手指过脏，或者移动过快等原因。");
                    }

                    @Override
                    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
                        super.onAuthenticationSucceeded(result);
                        LogUtil.logNormalMsg("指纹密码验证成功");
                        if (listener != null) {
                            listener.verificationSuccess();
                        }
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                            dialog.cancel();
                            dialog = null;
                            cancellationSignal.cancel();
                            cancellationSignal = null;
                            fingerprintManager = null;
                        }
                    }

                    @Override
                    public void onAuthenticationFailed() {
                        super.onAuthenticationFailed();
                        LogUtil.logNormalMsg("指纹验证失败，指纹识别失败，可再验，错误原因为：该指纹不是系统录入的指纹。");
                        if (listener != null) {
                            listener.verificationFail("指纹验证失败");
                        }
                    }
                },null);
            }
        }
        dialog.show();
        Window window = dialog.getWindow();
        assert window != null;
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.gravity = Gravity.CENTER;
        lp.width = ConvertUtils.dp2px(290);
        lp.height = ConvertUtils.dp2px(160);
        dialog.getWindow().setAttributes(lp);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cancellationSignal != null) {
                    cancellationSignal.cancel();
                    cancellationSignal = null;
                }
                dialog.dismiss();
                dialog.cancel();
                dialog = null;
                fingerprintManager = null;
            }
        });
    }
    private boolean supportFingerprint(Context context) {
        if (Build.VERSION.SDK_INT < 23) {
            RxToast.showTips("您的系统版本过低，不支持指纹功能");
            return false;
        } else {
            KeyguardManager keyguardManager = context.getSystemService(KeyguardManager.class);
            fingerprintManager = context.getSystemService(FingerprintManager.class);
            if (fingerprintManager == null || !fingerprintManager.isHardwareDetected()) {
                RxToast.showTips("您的手机不支持指纹功能");
                return false;
            } else if (!keyguardManager.isKeyguardSecure()) {
                RxToast.showTips("您还未设置锁屏，请先设置锁屏并添加一个指纹");
                return false;
            } else if (!fingerprintManager.hasEnrolledFingerprints()) {
                RxToast.showTips("您至少需要在系统设置中添加一个指纹");
                return false;
            }
        }
        return true;
    }

    /**
     * 改变网络环境
     * @param context context
     * @param listener 回调
     */
    public  void showChangeUrlDialog(Context context, final ChangeUrlListener listener){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        WeakReference<Context> reference = new WeakReference<>(context);
        View view = LayoutInflater.from(reference.get()).inflate(R.layout.change_url_dialog_layout, null);
        TextView onlineTv = view.findViewById(R.id.online_environment_tv);
        TextView developmentTv = view.findViewById(R.id.development_environment_tv);
        TextView testTv = view.findViewById(R.id.test_environment_tv);

        builder.setView(view);
        dialog = builder.create();
        dialog.show();

        Window window = dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.gravity = Gravity.CENTER;
        lp.width = ConvertUtils.dp2px(315);
        lp.height = ConvertUtils.dp2px(245);
        dialog.getWindow().setAttributes(lp);
        onlineTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                dialog.cancel();
                dialog = null;
                if (listener != null) {
                    listener.online();
                }
            }
        });
        testTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                dialog.cancel();
                dialog = null;
                if (listener != null) {
                    listener.test();
                }
            }
        });
        developmentTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                dialog.cancel();
                dialog = null;
                if (listener != null) {
                    listener.development();
                }

            }
        });
    }

    /**
     * 改变网络环境回调
     */
    public interface ChangeUrlListener{
        /**线上环境*/
        void online();
        /**测试环境*/
        void test();
        /**开发环境*/
        void development();
    }
    /**
     * 弹框按钮回调
     * @param <T> 泛型
     */
    public interface DialogConfirmListener<T>{
        /**
         * 点击确定
         * @param data
         */
        void confirm(T data);

        /**
         * 点击取消
         */
        void cancel();
    }
    /**
     * 指纹设置监听器
     */
    public interface DialogFingerprintListener{
        /**
         * 指纹验证成功
         */
        void verificationSuccess();
        /**
         * 指纹验证失败次数过多，稍后再试
         */
        void verificationLater();
        /**
         * 指纹验证失败
         * @param msg 失败信息
         */
        void verificationFail(String msg);
    }
    public interface DialogForgetPwdListener{
        /**
         * 通过手机号找回密码
         */
        void findPwdByPhone();

        /**
         * 通过邮箱找回密码
         */
        void findPwdByEmail();
    }
}