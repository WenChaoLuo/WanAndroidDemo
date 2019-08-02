package com.zc.demo.model

class BaseResponse<T>{
    var errorCode:Int = 0
    var errorMsg:String? = null
    var data:T? = null
}