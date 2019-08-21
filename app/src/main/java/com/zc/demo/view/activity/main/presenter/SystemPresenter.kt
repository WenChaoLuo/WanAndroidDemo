package com.zc.demo.view.activity.main.presenter

import com.zc.demo.connect.ConnectCallback
import com.zc.demo.connect.ConnectControl
import mvp.ljb.kt.presenter.BaseMvpPresenter
import com.zc.demo.view.activity.main.contract.SystemContract
import com.zc.demo.view.activity.main.model.SystemModel
import com.zc.utillibrary.LogUtil

/**
 * @Author Kotlin MVP Plugin
 * @Date 2019/08/05
 * @Description input description
 **/
class SystemPresenter : BaseMvpPresenter<SystemContract.IView, SystemContract.IModel>(), SystemContract.IPresenter{
     override fun loadData() {
        LogUtil.logNormalMsg("----------SystemPresenter-----loadData---")
        getSystemsData()
    }

    override fun registerModel() = SystemModel::class.java

    fun getSystemsData(){
        ConnectControl.getSystemData(object : ConnectCallback<List<SystemModel>>(){
            override fun success(data: List<SystemModel>) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun error(msg: String) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun noNetwork() {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })
    }

}
