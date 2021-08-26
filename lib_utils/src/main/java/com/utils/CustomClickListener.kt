package com.utils

import android.view.View

abstract class CustomClickListener : View.OnClickListener {
    private var mLastClickTime: Long = 0
    private var timeInterval = 1000L

    constructor()
    constructor(interval: Long) {
        timeInterval = interval
    }

    override fun onClick(v: View) {
        val nowTime = System.currentTimeMillis()
        if (nowTime - mLastClickTime > timeInterval) {
            onSingleClick(v)
            mLastClickTime = nowTime
        } else {
            onFastClick(v)
        }
    }

    protected abstract fun onSingleClick(v: View)
    
    protected open fun onFastClick(v: View) {}
}