package com.labo.utils;

import android.app.ActivityManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Vibrator;
import android.text.TextUtils;
import android.view.inputmethod.InputMethodManager;
/**
 * author : Naruto
 * desc   :
 * version:
 * getVersion               API版本号(如:14)
 * getOsVersion             系统版本号(如:4.0)
 * getPhoneModel            手机型号(如:Galaxy Nexus)
 * getAppVersionName        获取当前应用的VersionName
 * getVersionCode           获取当前应用的VersionCod
 * getProcessName           获取当前进程名称
 * isAppInstalled           检测某个应用是否安装
 * launchOrDownloadApp      下载或者打开App
 * goToMarket               去市场下载页面
 * getInputMethodManager    获取 InputMethodManager
 *
 */
public class AppUtils {

    // API版本号(如:14)
    public static int getVersion() {
        return Build.VERSION.SDK_INT;
    }

    // 系统版本号(如:4.0)
    public static String getOsVersion() {
        return Build.VERSION.RELEASE;
    }

    // 手机型号(如:Galaxy Nexus)
    public static String getPhoneModel() {
        return Build.MODEL;
    }

    /**
     * 获取当前应用的VersionName
     */
    public static String getAppVersionName(Context context) {
        String versionName = "";
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            if (TextUtils.isEmpty(versionName)) {
                versionName = "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionName;
    }

    /**
     * 获取当前应用的VersionCod
     */
    public static int getVersionCode(Context context) {
        int versionCode = 0;
        try {
            PackageInfo pInfo =context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            versionCode = pInfo.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionCode;
    }


    /**
     * 获取当前进程名称
     */
    public static String getProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (null == activityManager) {
            return "";
        }
        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager.getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return "";
    }

    /**
     * isAppInstalled 检测某个应用是否安装
     */
    public static boolean isAppInstalled(Context context, String packageName) {
        try {
            context.getPackageManager().getPackageInfo(packageName, 0);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * launchOrDownloadApp 下载或者打开App
     */
    public static void launchOrDownloadApp(Context context, String packageName) {
        // 判断是否安装过App，否则去市场下载
        if (isAppInstalled(context, packageName)) {
            context.startActivity(context.getPackageManager().getLaunchIntentForPackage(
                    packageName));
        } else {
            goToMarket(context, packageName);
        }
    }


    /**
     * goToMarket 去市场下载页面
     */
    public static void goToMarket(Context context, String packageName) {
        Uri uri = Uri.parse("market://details?id=" + packageName);
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            context.startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
        }
    }
    /**
     * 获取 InputMethodManager
     * @return {@link InputMethodManager}
     */
    public static InputMethodManager getInputMethodManager() {
        return getSystemService(Context.INPUT_METHOD_SERVICE);
    }
    /**
     * 获取 SystemService
     * @param name 服务名
     * @param <T>  泛型
     * @return SystemService Object
     */
    public static <T> T getSystemService(final String name) {
        try {
            return (T)   AppGlobals.INSTANCE.getContext().getSystemService(name);
        } catch (Exception e) {
        }
        return null;
    }
    /**
     * 获取 Vibrator
     * @return {@link Vibrator}
     */
    public static Vibrator getVibrator() {
        return getSystemService(Context.VIBRATOR_SERVICE);
    }

}
