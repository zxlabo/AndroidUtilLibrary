package com.demo.msg_demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import com.library.R

class HandlerDemoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_handler_demo)
        val handler=Handler(Looper.getMainLooper(),object :Handler.Callback{
            override fun handleMessage(msg: Message): Boolean {
                return true
            }

        })
        handler.post{

        }
        Looper.loop()
        val local=ThreadLocal<String>()
        local.set("设置值")
        local.get()//打印结果是：设置值
        local.remove()

    }
    /**
     * 主线程向子线程发送消息
     */

}