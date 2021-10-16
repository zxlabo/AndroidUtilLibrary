package com.common.base

import android.graphics.Color
import android.os.Bundle
import com.ui.activity.BaseToolBarActivity

abstract class BaseVmActivity<VM : BaseViewModel> : BaseToolBarActivity() {

    protected abstract val mVm: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initData()
        initObserver()
    }

    abstract fun initView()

    open fun initData() {

    }

    open fun initObserver() {

    }
    override fun getAppBarColor(): Int {
        return Color.parseColor("#23C5A3")
    }

    override fun getToolBarColor(): Int {
        return Color.parseColor("#23C5A3")
    }
}