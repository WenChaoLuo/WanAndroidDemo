package com.zc.demo.connect

/**
 * 网络请求回调
 * T 泛型
 */
abstract class ConnectCallback<T>{
    /**
     * 请求成功回调
     * data 数据
     */
    abstract fun success(data:T)

    /**
     * 请求失败回调
     * @param msg 失败信息
     */
    abstract fun error(msg:String)

    /**
     * 无网络回调
     */
    abstract fun noNetwork()

    /**
     * 连接超时
     */
    fun connectTimeout(){
        //连接超时
    }

    /**
     * 请求超时
     */
    fun timeout(){
        //请求超时
    }
}