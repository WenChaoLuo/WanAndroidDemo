package com.zc.demo.adapter

import android.content.Context
import com.blankj.utilcode.util.TimeUtils
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zc.demo.R
import com.zc.demo.view.activity.main.model.ProjectModel
import com.zc.utillibrary.LogUtil

/**
 * @className HomeArticleRvAdapter
 * @author wenchao
 * @date 2019/7/19
 * @version 1.0.1
 * @description
 */
class ProjectRvAdapter(layoutId:Int, data: List<ProjectModel.DataModel.DatasModel>,var context: Context) : BaseQuickAdapter<ProjectModel.DataModel.DatasModel, BaseViewHolder>(layoutId, data) {
    override fun convert(helper: BaseViewHolder?, item: ProjectModel.DataModel.DatasModel?) {
        helper!!.setText(R.id.project_item_title, item!!.title)
        helper.setText(R.id.project_item_info,item.desc)
        helper.setText(R.id.project_item_author_tv,item.author)
        helper.setText(R.id.project_item_time_tv,TimeUtils.millis2String(item.publishTime))
        LogUtil.logNormalMsg("item.envelopePic="+item.envelopePic)
        Glide.with(context).load(item.envelopePic).into(helper.itemView.findViewById(R.id.project_item_img))
//        helper.setImageResource(R.id.project_item_img,item.envelopePic)
//        helper.setText(R.id.rv_item_author_tv, item!!.author)
//        val time = TimeUtils.millis2String(item!!.publishTime!!)
//        helper.setText(R.id.rv_item_time_tv, time)
        helper.addOnClickListener(R.id.project_item_layout)

    }
}