//package com.common.base
//
//import android.annotation.SuppressLint
//import android.app.Application
//import android.content.Context
//import com.alibaba.android.arouter.launcher.ARouter
//import com.baselibrary.BuildConfig
//import com.baselibrary.R
//import com.orhanobut.logger.AndroidLogAdapter
//import com.orhanobut.logger.Logger
//import com.orhanobut.logger.PrettyFormatStrategy
//
///**
// * author : Naruto
// * date   : 2020-06-10
// * desc   :
// * version:
// */
//open class BaseApplication : Application() {
//
//    companion object {
//        @SuppressLint("StaticFieldLeak")
//        lateinit var mContext: Context
//    }
//
//    override fun onCreate() {
//        super.onCreate()
//        mContext = this
//        initLogger()
//        initARouter()
//    }
//
//    /**
//     * 初始化日志打印
//     */
//    private fun initLogger() { //DEBUG版本才打控制台log
//        if (BuildConfig.DEBUG) {
//            Logger.addLogAdapter(AndroidLogAdapter(PrettyFormatStrategy.newBuilder().tag(getString(R.string.app_name)).build()))
//        }
//    }
//
//    /**
//     * 初始化ARouter
//     */
//    private fun initARouter() {
//        if (BuildConfig.DEBUG) { //如果在debug模式下
//            // 打印日志,默认关闭
//            ARouter.openLog()
//            // 开启调试模式，默认关闭(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
//            ARouter.openDebug()
//            // 打印日志的时候打印线程堆栈
//            ARouter.printStackTrace()
//        }
//        // 尽可能早，推荐在Application中初始化
//        ARouter.init(this)
//    }
//
//}