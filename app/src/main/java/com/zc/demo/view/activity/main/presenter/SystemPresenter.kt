package com.zc.demo.view.activity.main.presenter

import mvp.ljb.kt.presenter.BaseMvpPresenter
import com.zc.demo.view.activity.main.contract.SystemContract
import com.zc.demo.view.activity.main.model.SystemModel

/**
 * @Author Kotlin MVP Plugin
 * @Date 2019/08/05
 * @Description input description
 **/
class SystemPresenter : BaseMvpPresenter<SystemContract.IView, SystemContract.IModel>(), SystemContract.IPresenter{
    override fun loadData() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun registerModel() = SystemModel::class.java

}
