package com.zc.demo.connect

import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import com.zc.demo.BuildConfig
import com.zc.demo.content.ServerUrl
import com.zc.demo.model.HomeArticleModel
import com.zc.demo.model.HomeBannerModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.internal.platform.Platform
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * 网络请求控制类
 */
class ConnectControl{
    /**
     * 静态方法和常量
     */
    companion object {
        /**网络请求服务*/
        lateinit var service:ConnectService
        /**okhttp builder*/
        lateinit var builder: OkHttpClient.Builder

        /**
         * 初始化okhttp retrofit service
         */
        private fun init(){
            //判断service是否已经初始化
            if (::service.isInitialized){
                return
            }
            //初始化网络请求日志打印  只在Debug调试模式下才打印
            var httpLoggingInterceptor: LoggingInterceptor? = null
            if (BuildConfig.DEBUG) {
                //debug模式下   打印网络请求日志
                httpLoggingInterceptor = LoggingInterceptor.Builder()
                        .loggable(BuildConfig.DEBUG)
                        .setLevel(Level.BASIC)
                        .log(Platform.INFO)
                        .request("Request")
                        .response("Response")
                        .build()
            }
            builder = OkHttpClient.Builder()
            //设置响应超时时间
            builder.readTimeout(9, TimeUnit.SECONDS)
            //设置连接超时时间
            builder.connectTimeout(10, TimeUnit.SECONDS)
            if (httpLoggingInterceptor != null) {
                builder.addInterceptor(httpLoggingInterceptor)
            }
            //初始化retrofit
            val retrofit = Retrofit.Builder().baseUrl(ServerUrl.BASE_URL)
                    .client(builder.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
            //初始化服务service
            service = retrofit.create(ConnectService::class.java)
        }

        /**
         * 获取首页banner
         */
        fun getHomeBanner(callback: ConnectCallback<List<HomeBannerModel>>){
            init()
            service.getHomeBanner().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(BaseObserver(callback))
        }

        /**
         * 获取首页文章
         */
        fun getHomeArticle(path:String,callback: ConnectCallback<HomeArticleModel>){
            init()
            service.getHomeArticle(path).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(BaseObserver(callback))
        }
    }

}