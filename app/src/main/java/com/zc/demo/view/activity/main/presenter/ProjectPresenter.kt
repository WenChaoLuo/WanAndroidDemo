package com.zc.demo.view.activity.main.presenter

import com.zc.demo.connect.ConnectCallback
import com.zc.demo.connect.ConnectControl
import mvp.ljb.kt.presenter.BaseMvpPresenter
import com.zc.demo.view.activity.main.contract.ProjectContract
import com.zc.demo.view.activity.main.model.ProjectModel
import com.zc.utillibrary.LogUtil

/**
 * @Author Kotlin MVP Plugin
 * @Date 2019/08/05
 * @Description input description
 **/
class ProjectPresenter : BaseMvpPresenter<ProjectContract.IView, ProjectContract.IModel>(), ProjectContract.IPresenter{
    override fun loadData() {
        ConnectControl.getProjects("1",object :ConnectCallback<ProjectModel.DataModel>(){
            override fun success(data: ProjectModel.DataModel) {
                getMvpView().refreshProjects(data.datas!!)
            }

            override fun error(msg: String) {
                getMvpView().showError(1)
            }

            override fun noNetwork() {
                getMvpView().showError(1)
            }
        })
    }

    override fun onCreate() {
        super.onCreate()
        loadData()
    }
    override fun registerModel() = ProjectModel::class.java

}
