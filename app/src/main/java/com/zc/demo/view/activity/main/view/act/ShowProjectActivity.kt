package com.zc.demo.view.activity.main.view.act

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.view.KeyEvent
import android.webkit.*
import com.zc.demo.R
import com.zc.demo.view.activity.mvp.BaseNormalActivity
import com.zc.utillibrary.RxToast
import com.zc.utillibrary.Util
import kotlinx.android.synthetic.main.activity_show_project.*

class ShowProjectActivity : BaseNormalActivity() {

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_project)
        val type = intent.getIntExtra("type",0)
        when (type) {
            0 -> {
                id_top_layout_title_tv.text = "文章"
            }
            1 -> {
                id_top_layout_title_tv.text = "项目"
            }
            else -> {

            }
        }
        id_top_layout_close_layout.setOnClickListener {
            finish()
        }
        id_top_layout_back_layout.setOnClickListener {
            back()
        }
        val url = intent.getStringExtra("url")

        webView.webViewClient = MyWebViewClient()
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.settings.useWideViewPort = true
        webView.settings.loadWithOverviewMode = true
        webView.settings.builtInZoomControls = true
        webView.webChromeClient = MyWebChromeClient()
        webView.loadUrl(url)
    }

    override fun onDestroy() {
        super.onDestroy()
        webView.destroy()
    }
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            back()
            return  true
        }
        return super.onKeyDown(keyCode, event)
    }

    private fun back() {
        if (webView.canGoBack()){
            webView.goBack()
        }else{
            finish()
        }
    }
    inner class MyWebChromeClient:WebChromeClient(){
        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            super.onProgressChanged(view, newProgress)
            progress.progress = newProgress
        }
    }
    inner class MyWebViewClient : WebViewClient(){
        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            Util.showView(progress,true)
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            Util.showView(progress,false)
        }

        override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
            super.onReceivedError(view, request, error)
            RxToast.showTips("加载失败")
        }
    }
}
