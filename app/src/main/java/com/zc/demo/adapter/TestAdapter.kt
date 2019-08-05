package com.zc.demo.adapter

import com.blankj.utilcode.util.TimeUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zc.demo.R
import com.zc.demo.view.activity.main.model.MainModel

/**
 * @className TestAdapter
 * @author wenchao
 * @date 2019/7/19
 * @version 1.0.1
 * @description
 */
class TestAdapter(layoutId:Int, data: List<MainModel.DataModel.DatasBean>) : BaseQuickAdapter<MainModel.DataModel.DatasBean, BaseViewHolder>(layoutId, data) {
    override fun convert(helper: BaseViewHolder?, item: MainModel.DataModel.DatasBean?) {
        helper!!.setText(R.id.rv_item_title_tv, item!!.title)
        helper.setText(R.id.rv_item_author_tv, item!!.author)
        val time = TimeUtils.millis2String(item!!.publishTime!!)
        helper.setText(R.id.rv_item_time_tv, time)
        helper.addOnClickListener(R.id.rv_item_layout)

    }
}