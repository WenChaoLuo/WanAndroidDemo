package com.zc.demo.view.activity.login.view.act

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import com.zc.demo.R
import com.zc.demo.view.activity.login.contract.LoginContract
import com.zc.demo.view.activity.login.presenter.LoginPresenter
import com.zc.demo.view.activity.main.view.act.MainActivity
import com.zc.demo.view.activity.mvp.BaseMvpActivity
import com.zc.utillibrary.LogUtil
import kotlinx.android.synthetic.main.login_activity.*
import kotlinx.android.synthetic.main.top_title_layout.*

/**
 * @Author Kotlin MVP Plugin
 * @Date 2019/08/02
 * @Description input description
 **/
class LoginActivity : BaseMvpActivity<LoginContract.IPresenter>(), LoginContract.IView, View.OnClickListener {
    override fun registerPresenter() = LoginPresenter::class.java

    override fun getLayoutId(): Int {
        LogUtil.logNormalMsg("-----------getLayoutId----------")
        return R.layout.login_activity
    }

    override fun onStart() {
        super.onStart()
        LogUtil.logNormalMsg("-------------onStart----------")
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        LogUtil.logNormalMsg("-------------onCreate----------")

    }

    override fun onResume() {
        super.onResume()
        LogUtil.logNormalMsg("--------------onResume----------")
        id_top_layout_title_tv.text = "登录"
        id_login_btn.setOnClickListener(this)
        id_top_layout_back_layout.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.id_login_btn -> {
                getPresenter().login()
                goActivity(MainActivity::class.java)
            }
            R.id.id_top_layout_back_layout -> {
                finish()
            }
            else -> {
                //do nothing
            }
        }
    }
}
