package com.apple.dance.activity

import android.os.Bundle
import com.apple.dance.R
import com.labo.lib.tool.log.*
import com.ui.activity.BaseToolBarActivity
import kotlinx.android.synthetic.main.activity_log_demo.*

class LogDemoActivity : BaseToolBarActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_demo)

        btn_console.setOnClickListener {
//            HiLogManager.getInstance().addPrinter(HiConsolePrinter(object : HiLogConfig() {}))
        }

        btn_view.setOnClickListener {
            val viewPrinter = HiViewPrinter(this, object : HiLogConfig() {})
            viewPrinter.viewProvider.showFloatingView()
            HiLogManager.getInstance().addPrinter(viewPrinter)
        }

        btn_file.setOnClickListener {
            val list = mutableListOf<String>()
            list.add("hello")
            HiLogManager.getInstance().addPrinter(HiFilePrinter.getInstance(list, object : HiLogConfig() {}))
        }

        btn_hello.setOnClickListener {
            HiLog.w("hello", "这是hello的内容")
        }

        btn_wordl.setOnClickListener {
            HiLog.w("word", "这是word的内容")
        }

    }
}