package com.zc.demo.view.activity.login.contract

import mvp.ljb.kt.contract.IPresenterContract
import mvp.ljb.kt.contract.IViewContract
import mvp.ljb.kt.contract.IModelContract

/**
 * @Author Kotlin MVP Plugin
 * @Date 2019/08/02
 * @Description input description
 **/
interface LoginContract {

    interface IView : IViewContract

    interface IPresenter : IPresenterContract{
        fun login()
    }

    interface IModel : IModelContract
}
