package com.zc.demo.view.activity.main.contract

import com.zc.demo.view.activity.main.model.ProjectModel
import mvp.ljb.kt.contract.IPresenterContract
import mvp.ljb.kt.contract.IViewContract
import mvp.ljb.kt.contract.IModelContract

/**
 * @Author Kotlin MVP Plugin
 * @Date 2019/08/05
 * @Description input description
 **/
interface ProjectContract {

    interface IView : IViewContract{
        fun refreshProjects(list: List<ProjectModel.DataModel.DatasModel>)
        /**
         * 显示空数据界面
         */
        fun showEmptyData()
        /**
         * 显示错误界面
         */
        fun showError(type:Int)
    }

    interface IPresenter : IPresenterContract

    interface IModel : IModelContract
}
