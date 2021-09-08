package com.ui.activity

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.appbar.AppBarLayout

/**
 * author : Naruto
 * desc   : ToolBar的工具类
 */

class ToolBarHelper {
    companion object {

        fun setToolBar(activity: AppCompatActivity, appbar: AppBarLayout, toolbar: Toolbar, appBarColor: Int,
            toolBarColor: Int, showBackButton: Boolean) {
            val barHeight = getStatusBarHeight(activity)
            appbar.setBackgroundColor(appBarColor)
            toolbar.setBackgroundColor(toolBarColor)
            activity.setSupportActionBar(toolbar)
            activity.supportActionBar?.setDisplayHomeAsUpEnabled(showBackButton) // 设置返回按钮可见
            activity.supportActionBar?.setDisplayShowHomeEnabled(true) // 设置是否显示logo图标
            activity.supportActionBar?.setHomeButtonEnabled(true) // 设置左上角的图标可点击
            appbar.setPadding(0, barHeight, 0, 0)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                appbar.elevation = 3f
            }
        }

        fun getStatusBarHeight(context: Context): Int {
            var statusBarHeight = 0
            val resourceId: Int =
                context.resources.getIdentifier("status_bar_height", "dimen", "android")
            if (resourceId > 0) {
                statusBarHeight = context.resources.getDimensionPixelSize(resourceId)
            }
            return statusBarHeight
        }

        /**
         * 设置状态栏透明
         */
        fun setTranslucentStatus(window: Window) {
            // 5.0以上系统状态栏透明
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val window = window
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                window.statusBarColor = Color.TRANSPARENT
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            }
        }

        /**
         * 设置状态栏颜色
         */
        @SuppressLint("ObsoleteSdkInt")
        fun setStatusBarColor(window: Window, color: Int) {
            // 5.0以上系统状态栏透明
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val window = window
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                window.decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                window.statusBarColor = color
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            }
        }
    }

}