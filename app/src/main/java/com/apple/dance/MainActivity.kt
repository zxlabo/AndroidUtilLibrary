package com.apple.dance

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.DialogFragment
import com.apple.dance.activity.TabTopActivity
import com.common.utils.router.HomeRouter
import com.apple.dance.activity.ThreadDemoActivity
import com.apple.dance.activity.AspectActivity
import com.apple.dance.activity.KeyboardActivity
import com.apple.dance.activity.WebActivity
import com.apple.dance.coroutine_demo.CoroutineActivity
import com.apple.dance.activity.HandlerDemoActivity
import com.apple.dance.room.DemoDataBase
import com.apple.dance.room.DemoTable
import com.labo.library.executor.LibExecutor
import com.apple.dance.activity.JavaActivity
import com.ui.activity.BaseToolBarActivity
import com.labo.lib.tool.utils.SpeechUtils
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
        btn_thread.setOnClickListener {
            startActivity(Intent(this, ThreadDemoActivity::class.java))
        }
        btn_annotation.setOnClickListener {
            startActivity(Intent(this, AspectActivity::class.java))
        }
        btn_java.setOnClickListener {
            startActivity(Intent(this, JavaActivity::class.java))
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
            SpeechUtils.getInstance(this).speakText("请最晚在11:50出发，去给王鹏送饭")
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