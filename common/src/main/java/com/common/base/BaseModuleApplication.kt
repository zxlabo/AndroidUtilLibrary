package com.common.base

abstract class BaseModuleApplication : BaseApplication() {
    override fun onCreate() {
        super.onCreate()
        initModuleApplication()
        initModuleData()
    }

    /**
     *  初始化模块的Application
     */
    public abstract fun initModuleApplication()

    /**
     * 所有组件的都初始化后再调用的方法
     */
    public abstract fun initModuleData()
}