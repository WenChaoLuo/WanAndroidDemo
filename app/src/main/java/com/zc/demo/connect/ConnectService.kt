package com.zc.demo.connect

import com.zc.demo.model.BaseResponse
import com.zc.demo.view.activity.main.model.MainModel
import com.zc.demo.view.activity.main.model.ProjectModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ConnectService{
    /**
     * 获取首页Banner
     */
    @GET("banner/json")
    fun getHomeBanner():Observable<BaseResponse<List<MainModel.BannerModel>>>

    /**
     * 获取首页文章内容
     * @param page 页数  用于分页查询
     */
    @GET("article/list/{page}/json")
    fun getHomeArticle(@Path("page") page: String):Observable<BaseResponse<MainModel.DataModel>>

    @GET("project/list/{page}/json")
    fun getProjects(@Path("page") page: String,@Query("cid") cid: String):Observable<BaseResponse<ProjectModel.DataModel>>
}