package com

import android.content.Intent
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.KeyEvent
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.activity.TabTopActivity
import com.common.utils.router.HomeRouter
import com.demo.activity.KeyboardActivity
import com.demo.activity.WebActivity
import com.demo.coroutine_demo.CoroutineActivity
import com.demo.coroutine_demo.printLog
import com.demo.msg_demo.HandlerDemoActivity
import com.demo.room.DemoDataBase
import com.demo.room.DemoTable
import com.library.BuildConfig
import com.library.R
import com.library.executor.LibExecutor
import com.ui.activity.BaseToolBarActivity
import com.utils.WifiUtils
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseToolBarActivity() {
    var i = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_home.setOnClickListener {
            HomeRouter.toHomeActivity()
        }
        btn_web.setOnClickListener {
            startActivity(Intent(this, WebActivity::class.java))
        }
        btn_coroutine.setOnClickListener {
            startActivity(Intent(this, CoroutineActivity::class.java))
        }
        btn_handler.setOnClickListener {
            startActivity(Intent(this, HandlerDemoActivity::class.java))
        }
        btn_tab_top.setOnClickListener {
            startActivity(Intent(this, TabTopActivity::class.java))
        }
        btn_keyboard.setOnClickListener {
            startActivity(Intent(this, KeyboardActivity::class.java))
        }

        btn_insert.setOnClickListener {
            LibExecutor.execute(runnable = {
                val bean = DemoTable()
                bean.cache_Key = "hello${i++}"
                DemoDataBase.get(this).cacheDao().insert(bean)
            })

        }
        btn_query.setOnClickListener {
            DemoDataBase.get(this).cacheDao().query2()
        }

        btn_test.setOnClickListener {
            printLog(WifiUtils.checkWifiIsEnable().toString())
        }

    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            //音量下键点击事件
            showDebugToolDialog()
        }
        return super.onKeyDown(keyCode, event)
    }

    private fun showDebugToolDialog() {
        if (BuildConfig.DEBUG) {
            try {
                val aClass = Class.forName("com.debugtool.DebugToolDialogFragment")
                val target: DialogFragment = aClass.getConstructor().newInstance() as DialogFragment
                target.show(supportFragmentManager, "debug_tool")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}