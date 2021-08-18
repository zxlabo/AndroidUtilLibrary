package com.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;


/**
 * author : Naruto
 * desc   : 状态栏
 * version:
     getStatusBarHeight       : 获取状态栏高度（px）
     isStatusBarVisible       : 判断状态栏是否可见
     setStatusBarLightMode    : 设置状态栏是否为浅色模式
     isStatusBarLightMode     : 判断状态栏是否为浅色模式
     setStatusBarColor        : 设置状态栏颜色
     getActionBarHeight       : 获取 ActionBar 高度
     setStatusBarVisibility   : 设置状态栏是否可见
 */

public class BarUtils {

    /**
     * 获取状态栏高度（px）
     */
    public static int getStatusBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        return resources.getDimensionPixelSize(resourceId);
    }

    /**
     * 获取 ActionBar 高度
     */
    public static int getActionBarHeight(Context context) {
        TypedValue tv = new TypedValue();
        if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            return TypedValue.complexToDimensionPixelSize(
                    tv.data, context.getResources().getDisplayMetrics()
            );
        }
        return 0;
    }


    /**
     * 判断状态栏是否为浅色模式
     */
    public static boolean isStatusBarLightMode(@NonNull final Activity activity) {
        return BaseBarUtils.isStatusBarLightMode(activity.getWindow());
    }

    /**
     * 设置状态栏是否为浅色模式
     */
    public static void setStatusBarLightMode(@NonNull final Activity activity, final boolean isLightMode) {
        BaseBarUtils.setStatusBarLightMode(activity.getWindow(), isLightMode);
    }


    /**
     * 设置状态栏颜色
     */
    public static View setStatusBarColor(@NonNull final Activity activity, @ColorInt final int color) {
        return BaseBarUtils.setStatusBarColor(activity, color, false);
    }

    /**
     * 判断状态栏是否可见
     */
    public static boolean isStatusBarVisible(@NonNull final Activity activity) {
        int flags = activity.getWindow().getAttributes().flags;
        return (flags & WindowManager.LayoutParams.FLAG_FULLSCREEN) == 0;
    }

    /**
     * 设置状态栏是否可见
     */
    public static void setStatusBarVisibility(@NonNull final Activity activity, Context context, final boolean isVisible) {
        BaseBarUtils.setStatusBarVisibility(activity.getWindow(), context, isVisible);
    }


}
