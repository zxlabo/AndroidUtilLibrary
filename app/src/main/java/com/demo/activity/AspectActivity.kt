package com.demo.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.library.R
import kotlinx.android.synthetic.main.activity_aspect.*

class AspectActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aspect)

        btn_1.setOnClickListener {
            Log.e("====", "点击事件")
        }

    }


}