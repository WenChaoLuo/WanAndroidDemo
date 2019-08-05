package com.zc.demo

import android.animation.Animator
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v4.widget.NestedScrollView
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.blankj.utilcode.util.ConvertUtils
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.zc.demo.adapter.TestAdapter
import com.zc.demo.connect.ConnectCallback
import com.zc.demo.connect.ConnectControl
import com.zc.demo.model.Ret
import com.zc.demo.view.activity.main.model.MainModel
import com.zc.utillibrary.DialogUtil
import com.zc.utillibrary.RxToast
import kotlinx.android.synthetic.main.activity_main.*

/**
 * @author  wenchao
 * @version  1.0.1
 * @date
 * @className:
 */
class MainActivity : AppCompatActivity(), BaseQuickAdapter.OnItemChildClickListener {
    lateinit var adapter: TestAdapter
    var data: List<MainModel.DataModel.DatasBean>? = null
    var showView: Boolean = false
    var hideView: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ConnectControl.getHomeBanner(object : ConnectCallback<List<MainModel.BannerModel>>() {
            override fun noNetwork() {
            }

            override fun success(data: List<MainModel.BannerModel>) {
                Log.e("main", "title=" + data[0].title)
            }

            override fun error(msg: String) {
                Log.e("main", "错误：msg=" + msg)

            }

        })
        ConnectControl.getHomeArticle("1", object : ConnectCallback<MainModel.DataModel>() {
            override fun noNetwork() {
            }

            override fun success(data: MainModel.DataModel) {
                Log.e("main", "title=" + data.datas!![0].title)
                initData(data.datas!!)
            }

            override fun error(msg: String) {
                Log.e("main", "错误：msg=" + msg)
            }
        })
    }

    private fun initData(datas: List<MainModel.DataModel.DatasBean>) {
        var d:MutableList<MainModel.DataModel.DatasBean> = mutableListOf()
        for(i in 0..6){
            d.add(datas[i])
        }
        this.data  = datas
        adapter = TestAdapter(R.layout.rv_item, datas)
        val managet: LinearLayoutManager = MyManager(this)
        managet.orientation = LinearLayoutManager.VERTICAL
        rv.adapter = adapter
        rv.layoutManager = managet
        adapter.onItemChildClickListener = this
    }

    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        Log.e("main", "点击了----" + this.data!![position].title)
        ToastUtils.showShort("点击了----" + this.data!![position].title)
        RxToast.success("点击了----" + this.data!![position].title)
        DialogUtil.getInstance().showFingerprintConfirmDialog(this,object :DialogUtil.DialogFingerprintListener{
            override fun verificationSuccess() {
            }

            override fun verificationLater() {
            }

            override fun verificationFail(msg: String?) {
            }
        })
        var intent = Intent()
        intent.setClass(this, Main4Activity::class.java)
        startActivity(intent)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            sv.smoothScrollTo(0, ConvertUtils.dp2px(60F))
        }
    }
    override fun onResume() {
        super.onResume()
        var handler = Handler()
        handler.postDelayed({
            Log.e("main","-----------------------执行移动")
            sv.smoothScrollTo(0, ConvertUtils.dp2px(60F))
        }, 1000)
        sv.setOnScrollChangeListener(object :MyScrollListener(){
            override fun hide() {
                Log.e("main", "-------------hide()" )
                if (!Ret.isVisibleLocal(searchLayoutTop,false)) {
                    Log.e("main", "----执行---------hide()" )
                    searchLayout.animate().translationY((-ConvertUtils.dp2px(60F)).toFloat()).setListener(object : AniListener() {
                        override fun onAnimationEnd(animation: Animator?) {
                            hideView = false
                        }
                    }).setDuration(500).start()
                }
            }

            override fun show() {
                Log.e("main", "-------------show()" )
                if (!Ret.isVisibleLocal(searchLayoutTop,false)) {
                    searchLayout.animate().translationY((ConvertUtils.dp2px(60F)).toFloat()).setDuration(500).setListener(object : AniListener() {
                        override fun onAnimationEnd(animation: Animator?) {
                            showView = false
                        }
                    }).start()
                }
            }

            override fun scrolling() {
                Log.e("main","Ret.isVisibleLocal(searchLayoutTop,true)="+Ret.isVisibleLocal(searchLayoutTop,true))
                if (Ret.isVisibleLocal(searchLayoutTop,true)) {
                    searchLayout.visibility = View.GONE
                    searchLayout.animate().translationY((-ConvertUtils.dp2px(60F)).toFloat()).setListener(object : AniListener() {
                        override fun onAnimationEnd(animation: Animator?) {
                            hideView = false
                        }
                    }).setDuration(500).start()
                }else{
                    searchLayout.visibility = View.VISIBLE
                }
            }
        })
    }

    class MyManager(context: Context) : LinearLayoutManager(context) {
        override fun canScrollVertically(): Boolean {
            return false
        }
    }

    abstract class AniListener : Animator.AnimatorListener {
        override fun onAnimationRepeat(animation: Animator?) {
        }

        override fun onAnimationCancel(animation: Animator?) {
        }

        override fun onAnimationStart(animation: Animator?) {
        }
    }

    abstract class MyScrollListener : NestedScrollView.OnScrollChangeListener {
        var hide = false
        var show = false
        var hideDistance = ConvertUtils.dp2px(4F)
        override fun onScrollChange(v: NestedScrollView?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int) {
            val y = scrollY - oldScrollY
            scrolling()
            if (Math.abs(y) > hideDistance) {
                if (y < 0){
                    if (!show){
                        show = true
                        show()
                        hide = false
                    }
                }else{
                    if (!hide){
                        hide = true
                        hide()
                        show = false
                    }
                }
            }
        }

        abstract fun hide()
        abstract fun show()
        abstract fun scrolling()
    }
}
