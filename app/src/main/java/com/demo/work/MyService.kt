package com.demo.work

import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.work.impl.utils.LiveDataUtils

class MyService: Service() {
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        
    }
}