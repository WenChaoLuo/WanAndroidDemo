package com.zc.demo.view.activity.main.contract

import com.zc.demo.view.activity.main.model.MainModel
import mvp.ljb.kt.contract.IPresenterContract
import mvp.ljb.kt.contract.IViewContract
import mvp.ljb.kt.contract.IModelContract

/**
 * @Author Kotlin MVP Plugin
 * @Date 2019/08/05
 * @Description input description
 **/
interface HomeContract {

    interface IView : IViewContract{
        /**
         * 刷新显示banner
         */
        fun refreshBanner(list: List<MainModel.BannerModel>)

        /**
         * 刷新显示文章
         */
        fun refreshArticle(data: MainModel.DataModel)

        /**
         * 显示内容
         */
        fun showContent()

        /**
         * 显示空数据界面
         */
        fun showEmptyData()
        /**
         * 显示错误界面
         */
        fun showError(type:Int)
        /**
         * 显示加载界面
         */
        fun showLoading()
        /**
         * 显示网络错误界面
         */
        fun showNetWorkError()
    }

    interface IPresenter : IPresenterContract

    interface IModel : IModelContract
}
