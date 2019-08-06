package com.zc.demo.view.activity.main.presenter

import com.zc.demo.connect.ConnectCallback
import com.zc.demo.connect.ConnectControl
import mvp.ljb.kt.presenter.BaseMvpPresenter
import com.zc.demo.view.activity.main.contract.MainContract
import com.zc.demo.view.activity.main.model.MainModel

/**
 * @Author Kotlin MVP Plugin
 * @Date 2019/08/02
 * @Description input description
 **/
class MainPresenter : BaseMvpPresenter<MainContract.IView, MainContract.IModel>(), MainContract.IPresenter{
    override fun loadData() {
    }

    override fun refresh() {
    }

    override fun registerModel() = MainModel::class.java
    override fun onCreate() {
        super.onCreate()

    }
}
