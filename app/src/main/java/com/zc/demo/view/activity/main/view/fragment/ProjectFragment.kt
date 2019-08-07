package com.zc.demo.view.activity.main.view.fragment

import android.os.Build
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.StaggeredGridLayoutManager
import android.text.Html
import android.view.View
import com.blankj.utilcode.util.ConvertUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.zc.demo.MainEvent
import com.zc.demo.R
import com.zc.demo.adapter.ProjectRvAdapter
import com.zc.demo.view.activity.main.contract.ProjectContract
import com.zc.demo.view.activity.main.model.ProjectModel
import com.zc.demo.view.activity.main.presenter.ProjectPresenter
import com.zc.utillibrary.*
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.android.synthetic.main.home_fragment.recyclerView
import kotlinx.android.synthetic.main.project_fragment.*
import mvp.ljb.kt.fragment.BaseMvpFragment
import org.greenrobot.eventbus.EventBus

/**
 * @Author Kotlin MVP Plugin
 * @Date 2019/08/05
 * @Description input description
 **/
class ProjectFragment : TabLayout.OnTabSelectedListener,BaseQuickAdapter.OnItemChildClickListener, BaseMvpFragment<ProjectContract.IPresenter>(), ProjectContract.IView {
    private var  data:List<ProjectModel.DataModel.DatasModel> ?= null
    private var  treeData:List<ProjectModel.TreeModel> ?= null
    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        val projectUrl = data?.get(position)?.link
        EventBus.getDefault().post(MainEvent(1, projectUrl!!))
    }

    override fun showEmptyData() {
        dialog.dismiss()
    }

    override fun showError(type: Int) {
        if (type == 2) {
            RxToast.showTips("获取Tab错误")
        }
        dialog.dismiss()
    }

    var dialog: LoadingDialog = LoadingDialog()
    override fun refreshProjects(list: List<ProjectModel.DataModel.DatasModel>) {
        data = list
        dialog.dismiss()
        val adapter = this.context?.let { ProjectRvAdapter(R.layout.project_rv_layout,list, it) }
        val managet = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = managet
        adapter?.onItemChildClickListener = this
        recyclerView.addItemDecoration(StaggeredDividerItemDecoration(context,ConvertUtils.dp2px(2f)))
    }
    override fun refreshProjectTree(list: List<ProjectModel.TreeModel>) {
        treeData = list
       list.forEach {
           run {
               val tab: TabLayout.Tab = tabLayout.newTab()
               if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N){
                   tab.text = Html.fromHtml(it.name)
               }else{
                   tab.text = Html.fromHtml(it.name,Html.FROM_HTML_MODE_COMPACT)
               }
               tabLayout.addTab(tab)
           }
       }
        tabLayout.addOnTabSelectedListener(this)
        dialog.dismiss()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dialog.show(context,null)
    }
    override fun registerPresenter() = ProjectPresenter::class.java

    override fun getLayoutId(): Int {
        return R.layout.project_fragment
    }

    override fun onTabReselected(tab: TabLayout.Tab?) {
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {
    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        val cid:String = this.treeData!![tab?.position!!].id.toString()
        getPresenter().getProjects(cid)
        dialog.show(context,null)
    }
}
