package com.zc.demo.view.activity.main.view.fragment

import com.zc.demo.R
import com.zc.demo.view.activity.main.contract.SystemContract
import com.zc.demo.view.activity.main.presenter.SystemPresenter
import mvp.ljb.kt.fragment.BaseMvpFragment

/**
 * @Author Kotlin MVP Plugin
 * @Date 2019/08/05
 * @Description input description
 **/
class SystemFragment : BaseMvpFragment<SystemContract.IPresenter>(), SystemContract.IView {

    override fun registerPresenter() = SystemPresenter::class.java

    override fun getLayoutId(): Int {
        return R.layout.empty_data_layout
    }
}
