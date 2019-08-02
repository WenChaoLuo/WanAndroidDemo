package com.zc.demo.connect

import com.zc.demo.model.BaseResponse
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * 自定义网络请求订阅者
 * 用于处理网络数据
 * T 泛型
 */
class BaseObserver<T> : Observer<BaseResponse<T>> {
    var callback: ConnectCallback<T>

    /**
     * 构造方法初始化
     */
    constructor(callback: ConnectCallback<T>) {
        this.callback = callback
    }

    /**
     * 网络请求完成
     */
    override fun onComplete() {
    }

    override fun onSubscribe(d: Disposable) {
    }

    /**
     * 网络请求成功
     */
    override fun onNext(t: BaseResponse<T>) {
        handleDataByCode(t, callback)
    }

    /**
     * 网络请求失败
     */
    override fun onError(e: Throwable) {
        val reg1 = "timeout"
        val reg2 = "failed to connect"
        if (reg1.contains(e.message!!)) {
            callback.timeout()
        } else if (reg2.contains(e.message!!)) {
            callback.connectTimeout()
        }
        callback.error(e.message!!)
    }

    /**
     * 根据code码判断数据返回
     * 这里可根据实际的结果码来判断响应 如未登录的结果码  回调未登录的响应方法
     * @param baseResponse 数据
     * @param callback 回调
     */
    private fun handleDataByCode(baseResponse: BaseResponse<T>, callback: ConnectCallback<T>) {
        when (baseResponse.errorCode) {
            0 -> {
                //结果码为0时成功获取数据
                callback.success(baseResponse.data!!)
            }
            else -> {
                //其他结果码回调
                callback.error("获取数据失败")
            }
        }
    }
}