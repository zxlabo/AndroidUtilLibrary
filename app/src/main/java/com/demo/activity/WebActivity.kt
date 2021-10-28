package com.demo.activity

import com.ui.activity.CommonWebViewActivity

class WebActivity : CommonWebViewActivity() {


    override fun initUrl(): String = "https://www.baidu.com/"
    override fun backClick() {
        onBackPressed()
    }
}