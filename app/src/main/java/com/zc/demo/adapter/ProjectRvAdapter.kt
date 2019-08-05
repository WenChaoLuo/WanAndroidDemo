package com.zc.demo.adapter

import android.content.Context
import com.blankj.utilcode.util.TimeUtils
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zc.demo.R
import com.zc.demo.view.activity.main.model.MainModel
import com.zc.demo.view.activity.main.model.ProjectModel

/**
 * @className TestAdapter
 * @author wenchao
 * @date 2019/7/19
 * @version 1.0.1
 * @description
 */
class ProjectRvAdapter(layoutId:Int, data: List<ProjectModel.DataModel.DatasModel>,var context: Context) : BaseQuickAdapter<ProjectModel.DataModel.DatasModel, BaseViewHolder>(layoutId, data) {
    override fun convert(helper: BaseViewHolder?, item: ProjectModel.DataModel.DatasModel?) {
        helper!!.setText(R.id.project_item_title, item!!.desc)
        Glide.with(context).load(item.envelopepic).into(helper.itemView.findViewById(R.id.project_item_img))
//        helper.setImageResource(R.id.project_item_img,item.envelopepic)
//        helper.setText(R.id.rv_item_author_tv, item!!.author)
//        val time = TimeUtils.millis2String(item!!.publishTime!!)
//        helper.setText(R.id.rv_item_time_tv, time)
        helper.addOnClickListener(R.id.project_item_layout)

    }
}