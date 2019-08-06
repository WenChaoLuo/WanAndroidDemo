package com.zc.demo.view.activity.main.view.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.zc.demo.MainEvent
import com.zc.demo.R
import com.zc.demo.adapter.HomeArticleRvAdapter
import com.zc.demo.util.GlideImageLoader
import com.zc.demo.view.activity.main.contract.HomeContract
import com.zc.demo.view.activity.main.model.MainModel
import com.zc.demo.view.activity.main.presenter.HomePresenter
import com.zc.utillibrary.LoadingDialog
import com.zc.utillibrary.RxToast
import kotlinx.android.synthetic.main.home_fragment.*
import mvp.ljb.kt.fragment.BaseMvpFragment
import org.greenrobot.eventbus.EventBus

/**
 * @Author Kotlin MVP Plugin
 * @Date 2019/08/05
 * @Description input description
 **/
class HomeFragment :BaseMvpFragment<HomeContract.IPresenter>(), BaseQuickAdapter.OnItemChildClickListener, HomeContract.IView {
    private var  data:List<MainModel.DataModel.DatasBean> ?= null
    lateinit var adapter: HomeArticleRvAdapter
    var dialog:LoadingDialog = LoadingDialog()
    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        val url = data?.get(position)?.link
        EventBus.getDefault().post(MainEvent(0, url!!))
    }


    override fun refreshBanner(list: List<MainModel.BannerModel>) {
        banner.setImageLoader(GlideImageLoader())
        var imgs:MutableList<String> ?= null
        imgs = mutableListOf()
        list.forEach {
            imgs.add(it.imagePath!!)
        }
        banner.setImages(imgs)
        banner.start()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onStart() {
        super.onStart()
    }
    override fun refreshArticle(data: MainModel.DataModel) {
        dialog.dismiss()
        if (data.datas!!.isEmpty()){
            showEmptyData()
        }else{
            this.data = data.datas
            adapter = HomeArticleRvAdapter(R.layout.rv_item, data.datas!!)
            val managet: LinearLayoutManager = LinearLayoutManager(this.context)
            managet.orientation = LinearLayoutManager.VERTICAL
            recyclerView.adapter = adapter
            recyclerView.layoutManager = managet
            adapter.onItemChildClickListener = this
        }
    }

    override fun showContent() {
    }

    override fun showEmptyData() {
    }

    override fun showError(type: Int) {
        dialog.dismiss()
    }

    override fun showLoading() {
    }

    override fun showNetWorkError() {
        dialog.dismiss()
    }

    override fun registerPresenter() = HomePresenter::class.java

    override fun getLayoutId(): Int {
        return R.layout.home_fragment
    }

}
