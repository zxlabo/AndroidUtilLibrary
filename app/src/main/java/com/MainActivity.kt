package com

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.fragment.app.DialogFragment
import com.activity.TabTopActivity
import com.bean.StudentBean
import com.common.utils.router.HomeRouter
import com.demo.activity.KeyboardActivity
import com.demo.activity.WebActivity
import com.demo.cor.CoroutineActivity
import com.library.BuildConfig
import com.library.R
import com.library.cache.LibStorage
import com.ui.activity.BaseToolBarActivity
import com.library.executor.LibExecutor
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseToolBarActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_home.setOnClickListener {
            HomeRouter.toHomeActivity()
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
        btn_coroutine.setOnClickListener {
            startActivity(Intent(this, CoroutineActivity::class.java))
        }
        btn_insert.setOnClickListener {
            LibExecutor.execute(runnable = {
                val bean = StudentBean(11, "xiaoming")
                LibStorage.saveCache("hello", bean)
            })

        }

        btn_query.setOnClickListener {
            LibExecutor.execute(runnable = {
                val bean = LibStorage.getCache<StudentBean>("hello")
                Log.e("===", bean?.toString()?:"")
            })

        }
        btn_test.setOnClickListener {

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