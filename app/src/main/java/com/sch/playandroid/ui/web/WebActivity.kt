package com.sch.playandroid.ui.web

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Html
import android.view.KeyEvent
import android.webkit.*
import com.sch.playandroid.R
import com.sch.playandroid.base.BaseActivity
import com.sch.playandroid.constants.Constants
import kotlinx.android.synthetic.main.activity_web.*

class WebActivity : BaseActivity() {
    private var loadUrl: String? = null
    private var title: String? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_web
    }

    override fun init(savedInstanceState: Bundle?) {
        val bundle: Bundle? = intent.extras
        loadUrl = bundle?.getString(Constants.WEB_URL)
        title = bundle?.getString(Constants.WEB_TITLE)
        tvTitle.text = Html.fromHtml(title)
        initWebView()
        ivBack.setOnClickListener {
            finish()
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView() {
        val webSettings: WebSettings = webView.settings
        webSettings.javaScriptEnabled = true
        //自适应屏幕
        webView.settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
        webView.settings.loadWithOverviewMode = true

        //如果不设置WebViewClient，请求会跳转系统浏览器
        webView.webViewClient = object : WebViewClient() {

            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                //返回false，意味着请求过程里，不管有多少次的跳转请求（即新的请求地址）
                //均交给webView自己处理，这也是此方法的默认处理
                return false
            }

            override fun shouldOverrideUrlLoading(
                view: WebView,
                request: WebResourceRequest
            ): Boolean {
                //返回false，意味着请求过程里，不管有多少次的跳转请求（即新的请求地址）
                //均交给webView自己处理，这也是此方法的默认处理

                return false
            }
        }

        webView?.loadUrl(loadUrl)
        loadingTip.loading()
        //webView加载成功回调
        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                if (newProgress == 100) loadingTip.dismiss()
            }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            //返回上个页面
            webView.goBack()
            return true
        }
        //退出H5界面
        return super.onKeyDown(keyCode, event)
    }
}