package com.zc.demo.connect

import com.zc.demo.model.BaseResponse
import com.zc.demo.model.HomeArticleModel
import com.zc.demo.model.HomeBannerModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface ConnectService{
    /**
     * 获取首页Banner
     */
    @GET("banner/json")
    fun getHomeBanner():Observable<BaseResponse<List<HomeBannerModel>>>

    /**
     * 获取首页文章内容
     * @param page 页数  用于分页查询
     */
    @GET("article/list/{page}/json")
    fun getHomeArticle(@Path("page") page: String):Observable<BaseResponse<HomeArticleModel>>
}