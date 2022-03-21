package com.debugtool

import android.content.Context
import android.os.Process
import com.labo.library.fps.FpsMonitor
import com.labo.utils.SPUtil


class DebugTools {

    fun buildEnvironment(): String {
        return "构建环境: " + if (BuildConfig.DEBUG) "测试环境" else "正式环境"
    }

    fun buildTime(): String {
        //new date() 当前你在运行时拿到的时间，这个包，被打出来的时间
        return "构建时间：" + BuildConfig.BUILD_TIME
    }

    @HiDebug(name = "一键开启Https降级", desc = "网络请求使用Http,可以使用抓包工具明文抓包")
    fun degrade2Http(context: Context) {
        SPUtil.putBoolean("degrade_http", true)
        val intent = context.packageManager.getLaunchIntentForPackage(context.packageName)
        context.startActivity(intent)

        //杀掉当前进程,并主动启动新的 启动页，以完成重启的动作
        Process.killProcess(Process.myPid())
    }

    @HiDebug(name = "打开/关闭Fps", desc = "打开后可以查看页面实时的FPS")
    fun toggleFps(context: Context) {
        FpsMonitor.toggle()
    }

//
//    fun buildDevice(): String {
//        // 构建版本 ： 品牌-sdk-abi
//        return "设备信息:" + Build.BRAND + "-" + Build.VERSION.SDK_INT + "-" + Build.CPU_ABI
//    }
//

//
//    @HiDebug(name = "查看Crash日志", desc = "可以一键分享给开发同学，迅速定位偶现问题")
//    fun crashLog(context: Context) {
//        val intent = Intent(context, CrashLogActivity::class.java)
//        context.startActivity(intent)
//    }
//
//

//
//
//    @HiDebug(name = "打开/关闭暗黑模式", desc = "打开暗黑模式在夜间使用更温和")
//    fun toggleTheme(context: Context) {
//        if (HiViewUtil.lightMode()) {
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
//        } else {
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//        }
//    }


}