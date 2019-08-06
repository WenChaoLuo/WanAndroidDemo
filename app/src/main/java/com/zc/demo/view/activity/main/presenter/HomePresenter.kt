package com.zc.demo.view.activity.main.presenter

import com.zc.demo.connect.ConnectCallback
import com.zc.demo.connect.ConnectControl
import mvp.ljb.kt.presenter.BaseMvpPresenter
import com.zc.demo.view.activity.main.contract.HomeContract
import com.zc.demo.view.activity.main.model.HomeModel
import com.zc.demo.view.activity.main.model.MainModel

/**
 * @Author Kotlin MVP Plugin
 * @Date 2019/08/05
 * @Description input description
 **/
class HomePresenter : BaseMvpPresenter<HomeContract.IView, HomeContract.IModel>(), HomeContract.IPresenter{
    override fun loadData() {
        ConnectControl.getHomeBanner(object : ConnectCallback<List<MainModel.BannerModel>>(){
            override fun success(data: List<MainModel.BannerModel>) {
                getMvpView().refreshBanner(data)
            }

            override fun error(msg: String) {
                getMvpView().showError(1)
            }

            override fun noNetwork() {
                getMvpView().showNetWorkError()
            }

        })
        ConnectControl.getHomeArticle("1",object : ConnectCallback<MainModel.DataModel>(){
            override fun success(data: MainModel.DataModel) {
                getMvpView().refreshArticle(data)
            }

            override fun error(msg: String) {
                getMvpView().showError(1)
            }

            override fun noNetwork() {
                getMvpView().showError(1)
            }
        })
    }

    override fun registerModel() = HomeModel::class.java

    override fun onCreate() {
        super.onCreate()
        loadData()
    }
}
