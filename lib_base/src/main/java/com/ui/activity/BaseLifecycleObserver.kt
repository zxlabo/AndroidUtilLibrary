package com.ui.activity

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

/**
 * author : Naruto
 * date   : 2019-09-26
 * 作用：管理Activity 和 Fragment 生命周期
 * 使用：
 * 1、使用LifecycleObserver，需要配置注解
 * 2、Google官方更推荐我们使用 DefaultLifecycleObserver 类，但是要实现
 *    implementation "androidx.lifecycle:lifecycle-common-java8:2.3.1"
 */
class BaseLifecycleObserver : DefaultLifecycleObserver {

    private var TAG = "BaseLifecycleObserver"

    private var lifecycleActivity: BaseLifecycleActivity? = null

    constructor()

    constructor(lifecycleActivity: BaseLifecycleActivity?) {
        this.lifecycleActivity = lifecycleActivity
        lifecycleActivity?.let { TAG = it::class.java.simpleName }
    }

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        this.lifecycleActivity =null
    }

}