package com.zc.demo.view.activity.main.contract

import com.zc.demo.view.activity.main.model.SystemModel
import mvp.ljb.kt.contract.IPresenterContract
import mvp.ljb.kt.contract.IViewContract
import mvp.ljb.kt.contract.IModelContract

/**
 * @Author Kotlin MVP Plugin
 * @Date 2019/08/05
 * @Description input description
 **/
interface SystemContract {

    interface IView : IViewContract{
        fun refreshSystemData(data: List<SystemModel>)
    }

    interface IPresenter : IPresenterContract

    interface IModel : IModelContract
}
