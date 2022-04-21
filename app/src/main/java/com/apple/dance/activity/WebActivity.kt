package com.apple.dance.activity

import com.ui.activity.CommonWebViewActivity

class WebActivity : CommonWebViewActivity() {


    override fun initUrl(): String = "file:///android_asset/select_file.html"

    override fun backClick() {
        onBackPressed()
    }

}