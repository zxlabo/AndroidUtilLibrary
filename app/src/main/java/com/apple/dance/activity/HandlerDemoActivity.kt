package com.apple.dance.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.widget.Toast
import com.apple.dance.R
import kotlinx.android.synthetic.main.activity_handler_demo.*
import kotlin.concurrent.thread

class HandlerDemoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_handler_demo)

        btn_toast.setOnClickListener {
            thread {
                //添加 Looper.prepare()，对Looper进行创建初始化。但是添加了这一行，只是不报错。但没有弹出toast
                if (Looper.myLooper()==null){
                    Looper.prepare()
                }
                Toast.makeText(this, "hhh", Toast.LENGTH_SHORT).show()
                //Looper.loop() 要加在后面。为什么呢？因为当我们调用show方法的时候。会将消息插入到消息队列中。
                // 如果先调用Looper.loop()，此时消息队列中没有消息，当前线程的Looper会陷入阻塞。不会被唤醒。
                // 只有通过其他线程往当前消息队列中插消息才会被唤醒。
                Looper.loop()
            }
        }


        btn_toast2.setOnClickListener {
            Toast.makeText(this, "hhh", Toast.LENGTH_SHORT).show()
        }
    }
    /**
     * 主线程向子线程发送消息
     */

}