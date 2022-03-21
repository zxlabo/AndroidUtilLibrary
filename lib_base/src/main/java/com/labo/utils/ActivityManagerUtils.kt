package com.labo.utils

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import java.util.*
import kotlin.system.exitProcess

/**
 * author : Naruto
 * desc   : Activity管理器
 * version:
 * addActivity          Activity入栈
 * finishActivity       Activity出栈
 * currentActivity      获取当前栈顶Activity
 * finishAllActivity    清理栈
 * exitApp              退出应用程序
 */

class ActivityManagerUtils private constructor() {

    private val activityStack: Stack<Activity> = Stack()

    companion object {
        val instance: ActivityManagerUtils by lazy { ActivityManagerUtils() }
    }

    /**
     * Activity入栈
     */
    fun addActivity(activity: Activity) {
        activityStack.add(activity)
    }

    /**
     * Activity出栈
     */
    fun finishActivity(activity: Activity) {
        activity.finish()
        activityStack.remove(activity)
    }

    /**
     * 获取当前栈顶Activity
     */
    fun currentActivity(): Activity {
        return activityStack.lastElement()
    }

    /**
     * 清理栈
     */
    fun finishAllActivity() {
        for (activity in activityStack) {
            activity.finish()
        }
        activityStack.clear()
    }

    /**
     * 退出应用程序
     */
    fun exitApp(context: Context) {
        finishAllActivity()
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        activityManager.killBackgroundProcesses(context.packageName)
        exitProcess(0)
    }
}
