package com.zc.demo.util

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.youth.banner.loader.ImageLoader

/**
 * @className GlideImageLoader
 * @author wenchao
 * @date 2019/8/5
 * @version 1.0.1
 * @description
 */
class GlideImageLoader : ImageLoader() {
    override fun displayImage(context: Context?, path: Any?, imageView: ImageView?) {
       Glide.with(context!!).load(path).into(imageView!!)
    }
}