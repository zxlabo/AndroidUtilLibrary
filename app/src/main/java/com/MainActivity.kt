package com

import android.content.Intent
import android.os.Bundle
import com.activity.RefreshActivity
import com.activity.TabTopActivity
import com.common.utils.router.HomeRouter
import com.demo.cor.CoroutineActivity
import com.demo.activity.KeyboardActivity
import com.demo.activity.WebActivity
import com.library.R
import com.ui.activity.BaseToolBarActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseToolBarActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_home.setOnClickListener {
            HomeRouter.toHomeActivity()
        }
        btn_refresh.setOnClickListener {
            startActivity(Intent(this, RefreshActivity::class.java))
        }
        btn_tab_top.setOnClickListener {
            startActivity(Intent(this, TabTopActivity::class.java))
        }
        btn_keyboard.setOnClickListener {
            startActivity(Intent(this, KeyboardActivity::class.java))
        }
        btn_web.setOnClickListener {
            startActivity(Intent(this, WebActivity::class.java))
        }
        btn_test.setOnClickListener {
            startActivity(Intent(this, CoroutineActivity::class.java))

        }
    }
}