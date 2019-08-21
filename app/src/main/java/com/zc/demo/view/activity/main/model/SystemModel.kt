package  com.zc.demo.view.activity.main.model

import com.zc.demo.view.activity.main.contract.SystemContract
import mvp.ljb.kt.model.BaseModel

/**
 * @Author Kotlin MVP Plugin
 * @Date 2019/08/05
 * @Description input description
 **/
class SystemModel : BaseModel(), SystemContract.IModel{
    var children: List<ChildrenBean> ?= null
    var courseId: Int = 0
    var id: Int = 0
    var order: Int = 0
    var parentChapterId: Int = 0
    var userControlSetTop: Boolean = false
    var visible: Int = 0
    var name: String ?= null
    inner class ChildrenBean{
        var courseId: Int = 0
        var id: Int = 0
        var order: Int = 0
        var parentChapterId: Int = 0
        var userControlSetTop: Boolean = false
        var visible: Int = 0
        var name: String ?= null
    }
}