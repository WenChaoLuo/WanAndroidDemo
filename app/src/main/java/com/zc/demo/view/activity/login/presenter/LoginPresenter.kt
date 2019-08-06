package com.zc.demo.view.activity.login.presenter

import mvp.ljb.kt.presenter.BaseMvpPresenter
import com.zc.demo.view.activity.login.contract.LoginContract
import com.zc.demo.view.activity.login.model.LoginModel

/**
 * @Author Kotlin MVP Plugin
 * @Date 2019/08/02
 * @Description input description
 **/
class LoginPresenter : BaseMvpPresenter<LoginContract.IView, LoginContract.IModel>(), LoginContract.IPresenter{
    override fun loadData() {
    }

    override fun registerModel() = LoginModel::class.java
    override fun onCreate() {
        super.onCreate()
    }
    override fun login(){
    }
}
