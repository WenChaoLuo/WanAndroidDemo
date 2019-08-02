package com.zc.demo.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zc.demo.R
import com.zc.demo.model.HomeArticleModel

/**
 * @className TestAdapter
 * @author wenchao
 * @date 2019/7/19
 * @version 1.0.1
 * @description
 */
class TestAdapter(layoutId:Int, data: List<HomeArticleModel.DatasBean>) : BaseQuickAdapter<HomeArticleModel.DatasBean, BaseViewHolder>(layoutId, data) {
    override fun convert(helper: BaseViewHolder?, item: HomeArticleModel.DatasBean?) {
        helper!!.setText(R.id.rv_item_tv, item!!.title)
        helper.addOnClickListener(R.id.rv_item_layout)
    }
}