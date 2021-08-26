package com.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ui.helper.BaseLifecycleObserver

/**
 * author : Naruto
 * date   : 2019-07-25
 * desc   :
 * 作用：如果一个控件或代码，需要生命周期控制，就可以使用LifecycleObserver。
 * 使用：
 * 1、自定义Observer实现BaseLifecycleObserver。
 * 2、在createObserver方法中返回自定义Observer。
 *
 */

open class BaseLifecycleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (createObserver() != null) {
            lifecycle.addObserver(createObserver()!!)
        }
        createObserverList()?.forEach {observer->
            lifecycle.addObserver(observer)
        }
    }

    open fun createObserver(): BaseLifecycleObserver? {
        return null
    }

    open fun createObserverList(): Array<BaseLifecycleObserver>? {
        return null
    }

}