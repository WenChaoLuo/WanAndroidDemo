package com.zc.demo.view.activity.main.view.fragment

import com.zc.demo.R
import com.zc.demo.view.activity.main.contract.MineContract
import com.zc.demo.view.activity.main.presenter.MinePresenter
import mvp.ljb.kt.fragment.BaseMvpFragment

/**
 * @Author Kotlin MVP Plugin
 * @Date 2019/08/05
 * @Description input description
 **/
class MineFragment : BaseMvpFragment<MineContract.IPresenter>(), MineContract.IView {

    override fun registerPresenter() = MinePresenter::class.java

    override fun getLayoutId(): Int {
        return R.layout.empty_data_layout
    }
}
