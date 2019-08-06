package com.zc.demo.adapter

import android.os.Build
import android.support.annotation.RequiresApi
import android.text.Html
import android.text.Html.FROM_HTML_MODE_COMPACT
import com.blankj.utilcode.util.TimeUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zc.demo.R
import com.zc.demo.view.activity.main.model.MainModel

/**
 * @className HomeArticleRvAdapter
 * @author wenchao
 * @date 2019/7/19
 * @version 1.0.1
 * @description
 */
class HomeArticleRvAdapter(layoutId:Int, data: List<MainModel.DataModel.DatasBean>) : BaseQuickAdapter<MainModel.DataModel.DatasBean, BaseViewHolder>(layoutId, data) {
    override fun convert(helper: BaseViewHolder?, item: MainModel.DataModel.DatasBean?) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N){
            helper!!.setText(R.id.rv_item_title_tv, Html.fromHtml(item!!.title))
        }else{
            helper!!.setText(R.id.rv_item_title_tv, Html.fromHtml(item!!.title,FROM_HTML_MODE_COMPACT))
        }

        helper.setText(R.id.rv_item_author_tv, item.author)
        val time = TimeUtils.millis2String(item.publishTime!!)
        helper.setText(R.id.rv_item_time_tv, time)
        helper.addOnClickListener(R.id.rv_item_layout)

    }
}