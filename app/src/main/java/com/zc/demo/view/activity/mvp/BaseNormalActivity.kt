package com.zc.demo.view.activity.mvp

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import com.leaf.library.StatusBarUtil
import com.zc.demo.R

/**
 * @className BaseNormalActivity
 * @author wenchao
 * @date 2019/8/6
 * @version 1.0.1
 * @description
 */
@SuppressLint("Registered")
open class BaseNormalActivity :Activity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.title_bar_color))
        StatusBarUtil.setLightMode(this)
    }
}