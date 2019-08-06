package com.zc.demo.view.activity.main.presenter

import mvp.ljb.kt.presenter.BaseMvpPresenter
import com.zc.demo.view.activity.main.contract.MineContract
import com.zc.demo.view.activity.main.model.MineModel

/**
 * @Author Kotlin MVP Plugin
 * @Date 2019/08/05
 * @Description input description
 **/
class MinePresenter : BaseMvpPresenter<MineContract.IView, MineContract.IModel>(), MineContract.IPresenter{
    override fun loadData() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun registerModel() = MineModel::class.java

}
