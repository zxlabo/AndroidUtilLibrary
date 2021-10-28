package com.ui.activity

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.net.Uri
import android.net.http.SslError
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import com.utils.R
import com.utils.ext.showToast

/**
 * author : Naruto
 * date   : 2019-10-22
 * desc   :
 * version:
 */
abstract class CommonWebViewActivity : BaseToolBarActivity() {
    private var mWebView: WebView? = null
    private lateinit var webUrl: String
    private lateinit var mFrameLayout: FrameLayout
    private var mProgressBar: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //解决WebView截长屏不全的问题
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            try {
                WebView.enableSlowWholeDocumentDraw()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        setContentView(getLayout())
        mFrameLayout = getFrameLayout()
        mProgressBar = getProgressBar()
        addWebView()
        initWebView()
        loadUrl(initUrl())
    }

    fun loadUrl(url: String?) {
        if (!TextUtils.isEmpty(url)){
            webUrl =url!!
            mWebView?.loadUrl(webUrl)
        }
    }

    abstract fun initUrl(): String

    open fun getLayout(): Int = R.layout.common_web_view

    @SuppressLint("WrongViewCast")
    open fun getFrameLayout(): FrameLayout {
        return findViewById<FrameLayout>(R.id.fl_web_view)
    }

    @SuppressLint("WrongViewCast")
    open fun getProgressBar(): ProgressBar? {
        return findViewById(R.id.progress_bar)
    }

    /**
     * 代码动态添加WebView
     */
    private fun addWebView() {
        val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        mWebView = WebView(applicationContext)
        mWebView?.layoutParams = params
        //设置WebView取消右边滚动条和水波纹效果
        mWebView?.overScrollMode = View.OVER_SCROLL_NEVER
        mWebView?.isVerticalScrollBarEnabled = false
        mFrameLayout.addView(mWebView)
    }


    /**
     * 初始化WebView
     */
    @SuppressLint("SetJavaScriptEnabled", "ObsoleteSdkInt", "JavascriptInterface")
    private fun initWebView() {
        mWebView?.let {
            //对WebView进行配置和管理
            val webSettings = it.settings
            //设置与JS交互的权限
            webSettings.javaScriptEnabled = true
            //设置允许JS弹窗
            webSettings.javaScriptCanOpenWindowsAutomatically = true
            //将图片调整到适合WebView的大小
            webSettings.useWideViewPort = true
            //缩放至屏幕大小
            webSettings.loadWithOverviewMode = true
            //设置编码格式
            webSettings.defaultTextEncodingName = "UTF-8"
            //启用地理定位
            webSettings.setGeolocationEnabled(true)
            //允许WebView使用File协议
            webSettings.allowFileAccess = true
            //解决js跨域问题
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                webSettings.allowUniversalAccessFromFileURLs = true
            }
            //5.1以上默认禁止了https和http的混用，下面的设置是开启
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                webSettings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            }
            //开启DOM storage API功能
            webSettings.domStorageEnabled = true
            //开启database storage API功能
            webSettings.databaseEnabled = true
            //设置缓存的模式
            webSettings.cacheMode = getCacheMode()
            it.webViewClient = MyWebViewClient()
            it.webChromeClient = MyWebChromeClient()
        }
    }


    /**
     * 页面刷新
     */
    fun refreshView() {
        if (mWebView != null && !TextUtils.isEmpty(webUrl)) {
            mWebView?.loadUrl(webUrl)
        }
    }

    /**
     * 获取WebView缓存的模式
    LOAD_CACHE_ONLY: 不使用网络，只读取本地缓存数据
    LOAD_DEFAULT: （默认）根据cache-control决定是否从网络上取据。
    LOAD_NO_CACHE: 不使用缓存，只从网络获取数据.
    LOAD_CACHE_ELSE_NETWORK，只要本地有，无论是否过期，或no-cache，都使用缓存中的数据。
     */
    private fun getCacheMode(): Int {
        return WebSettings.LOAD_NO_CACHE
    }

    /**
     * 销毁WebView
     * 在关闭了Activity时，如果WebView的音乐或视频，还在播放。就必须销毁WebView.
     * 注意：WebView调用onDestroy时,WebView仍绑定在Activity上
     * 这是由于自定义WebView构建时传入了该Activity的context对象,因此需要先从父容器中移除WebView,然后再销毁WebView:
     */
    override fun onDestroy() {
        super.onDestroy()
        mWebView?.let {
            it.loadDataWithBaseURL("", "", "text/html", "utf-8", null)
            //清除当前WebView访问的历史记录
            it.clearHistory()
            val parent = it.parent as ViewGroup
            //从父容器中移除WebView
            parent.removeView(it)
            it.destroy()
        }
    }

    /**
     * 对返回键的监听，实现网页后退
     */

    override fun onBackPressed() {
        if (mWebView!=null&&mWebView!!.canGoBack()){
            mWebView!!.goBack()
        }else{
            super.onBackPressed()
        }
    }

    /**
     * WebViewClient
     * 作用：处理各种通知，请求事件，主要有:网页开始加载，加载结束，加载错误(如404)，处理https请求
     */
    inner class MyWebViewClient : WebViewClient() {
        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            //页面开始的时候，设置不加载图片
            view?.settings?.blockNetworkImage = true
        }

        //页面加载结束
        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            //页面加载结束，开始加载图片
            view?.settings?.blockNetworkImage = false
            val automatically = view?.settings?.loadsImagesAutomatically
            automatically?.let {
                if (!it) {
                    //设置wenView加载图片资源
                    view.settings.blockNetworkImage = false
                    view.settings.loadsImagesAutomatically = true
                }
            }
        }


        //加载页面的服务器出现错误时（如404）调用。
        override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
            webViewLoadError(view,request,error)
        }

        //webView默认是不处理https请求的，页面显示空白，需要进行如下设置
        override fun onReceivedSslError(view: WebView, handler: SslErrorHandler, error: SslError) {
            handler.proceed()  // 接受所有网站的证书
            //handler.cancel(); // Android默认的处理方式
            //handleMessage(Message msg); // 进行其他处理
        }

        /**
         * 返回true，取消当前加载。否则返回false。
         * super.shouldOverrideUrlLoading(view, request)默认返回false
         */
        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
            //JS调用Android，对协议进行处理
            return dealUrl(view, request)
        }

    }

    private fun webViewLoadError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {


    }

    open fun dealUrl(view: WebView?, request: WebResourceRequest?): Boolean = false

    /**
     * WebChromeClient
     * 可以得到网页加载的进度，网页的标题，网页的icon,js的一些弹框，
     */
    inner class MyWebChromeClient : WebChromeClient() {
        //网页加载的进度
        override fun onProgressChanged(view: WebView, progress: Int) {
            mProgressBar?.visibility = View.VISIBLE
            mProgressBar?.progress = progress
            if (progress >= 90) {
                mProgressBar?.visibility = View.GONE
            }
        }

        //网页的标题
        override fun onReceivedTitle(view: WebView, title: String) {
            super.onReceivedTitle(view, title)
            setTitle(title)
        }

        //js alert
        override fun onJsAlert(view: WebView?, url: String?, message: String?, result: JsResult?): Boolean {
            AlertDialog.Builder(this@CommonWebViewActivity)
                .setTitle("JsAlert")
                .setMessage(message)
                .setPositiveButton("OK") { _, _ -> result?.confirm() }
                .setCancelable(false)
                .show()
            return true
        }


        override fun onJsPrompt(view: WebView?, url: String?, message: String?, defaultValue: String?, result: JsPromptResult?): Boolean {
            //先判断页面是否关闭
            showToast(message.toString())
            val uri = Uri.parse(message)
            if (uri.scheme == "js") {
                // 将需要返回的值通过该方式返回
                result?.confirm("js调用了Android的方法")
                return true
            }
            return super.onJsPrompt(view, url, message, defaultValue, result)
        }
    }

}


