package com.labo.utils.ext

import android.view.View
import android.view.animation.AnimationUtils

/**
 * author : Naruto
 * desc   : 动画工具类
 * startTweenAnimation  ： 加载补间动画
 */


/**
 * 加载补间动画
 */
fun View.startTweenAnimation(animSource: Int) {
    //从xml文件中获取动画
    try {
        val animation = AnimationUtils.loadAnimation(this.context, animSource)
        this.startAnimation(animation)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}