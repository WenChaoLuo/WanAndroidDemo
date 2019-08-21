package com.zc.demo.view.activity.mvp

import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.widget.Toast
import com.leaf.library.StatusBarUtil
import mvp.ljb.kt.contract.IPresenterContract
import mvp.ljb.kt.view.MvpFragmentActivity
import android.os.Build
import android.view.View
import com.zc.demo.R
import com.zc.utillibrary.LogUtil


/**
 * Author:Ljb
 * Time:2019/7/4
 * There is a lot of misery in life
 **/
abstract class BaseMvpFragmentActivity<out P : IPresenterContract> : MvpFragmentActivity<P>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        init(savedInstanceState)
        initView()
        initData()
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.title_bar_color))
        StatusBarUtil.setLightMode(this)
        hideBottomUIMenu()
    }

    protected abstract fun getLayoutId(): Int

    protected open fun init(savedInstanceState: Bundle?) {}

    protected open fun initView() {}

    protected open fun initData() {}

    override fun getResources(): Resources {
        val res = super.getResources()
        val newConfig = Configuration()
        newConfig.setToDefaults()
        res.updateConfiguration(newConfig, res.displayMetrics)
        return res
    }

    override fun showToast(resId: Int) {
        Toast.makeText(this, resId, Toast.LENGTH_SHORT).show()
    }

    override fun showToast(text: String?) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    protected open fun goActivity(cls: Class<*>, bundle: Bundle?) {
        val intent = Intent(this, cls)
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        startActivity(intent)
    }

    protected open fun goActivity(cls: Class<*>) {
        goActivity(cls, null)
    }
    private fun hideBottomUIMenu() {
        LogUtil.logNormalMsg("----------------------------隐藏虚拟按键，并且全屏")
        //隐藏虚拟按键，并且全屏
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            val v = window.decorView
            v.systemUiVisibility = View.GONE
        } else if (Build.VERSION.SDK_INT >= 21) {
            //for new api versions.这种方式虽然是官方推荐，但是根本达不到效果
            val decorView = window.decorView
            val uiOptions = (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or View.SYSTEM_UI_FLAG_FULLSCREEN)
            decorView.systemUiVisibility = uiOptions
        }
    }
}