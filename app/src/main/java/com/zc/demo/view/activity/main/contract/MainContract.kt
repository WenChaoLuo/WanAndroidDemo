package com.zc.demo.view.activity.main.contract

import com.zc.demo.view.activity.main.model.MainModel
import mvp.ljb.kt.contract.IPresenterContract
import mvp.ljb.kt.contract.IViewContract
import mvp.ljb.kt.contract.IModelContract

/**
 * @Author Kotlin MVP Plugin
 * @Date 2019/08/02
 * @Description input description
 **/
interface MainContract {

    interface IView : IViewContract{

    }

    interface IPresenter : IPresenterContract{
        fun refresh()
    }

    interface IModel : IModelContract
}
