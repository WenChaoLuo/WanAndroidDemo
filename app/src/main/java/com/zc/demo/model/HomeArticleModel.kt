package com.zc.demo.model

class HomeArticleModel{
    var curPage:Int ?= 0
    var datas:List<DatasBean> ?= null
    var offset:Int ?= null
    var over:Boolean ?= false
    var pageCount:Int ?= 0
    var size:Int ?= 0
    var total:Int ?= 0
    class DatasBean{
        var apkLink:String ?= null
        var author:String ?= null
        var chapterId:String ?= null
        var chapterName:String ?= null
        var collect:String ?= null
        var courseId:String ?= null
        var desc:String ?= null
        var envelopePic:String ?= null
        var fresh:String ?= null
        var id:String ?= null
        var link:String ?= null
        var niceDate:String ?= null
        var origin:String ?= null
        var prefix:String ?= null
        var projectLink:String ?= null
        var publishTime:String ?= null
        var superChapterId:String ?= null
        var superChapterName:String ?= null
        var title:String ?= null
        var type:String ?= null
        var visible:String ?= null
        var userId:String ?= null
        var zan:String ?= null
    }
}