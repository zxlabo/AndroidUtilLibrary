package com.labo.lib.tool.utils.ext

import android.view.View
import com.labo.lib.tool.utils.CustomClickListener

inline fun View.setOnAvoidClickListener(crossinline onSingleClick: (v: View) -> Unit) {
    this.setOnClickListener(object : CustomClickListener() {
        override fun onSingleClick(v: View) {
            onSingleClick.invoke(v)
        }
    })
}

inline fun View.setOnAvoidClickListener(interval: Long, crossinline onSingleClick: (v: View) -> Unit) {
    this.setOnClickListener(object : CustomClickListener(interval) {
        override fun onSingleClick(v: View) {
            onSingleClick.invoke(v)
        }
    })
}