package com.demo.activity.annotation;

import android.app.Activity;
import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.os.Bundle;
import android.util.Log;

/**
 * @author zhouxin
 * 自己监听ActivityLifecycleCallbacks
 */
public class PageExposure {

    private static final String TAG = "统计页面事件";

    private static boolean init = false;

    /**
     * 在日志sdk初始化之后再调用
     *
     * @param application
     */
    public static void init(Application application) {
        if (init) {
            return;
        }
        init = true;
        application.registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                onReportEvent(activity.getClass().getName(), "onActivityCreated");
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {
                onReportEvent(activity.getClass().getName(), "onActivityResumed");
            }

            @Override
            public void onActivityPaused(Activity activity) {
                onReportEvent(activity.getClass().getName(), "onActivityPaused");
            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                onReportEvent(activity.getClass().getName(), "onActivityDestroyed");
            }
        });
    }

    /**
     * 打印统计到的事件
     */
    private static void onReportEvent(String pageId, String event) {
        printLog("page：" + pageId + ",event：" + event);

    }

    private static void printLog(String msg) {
        Log.e(TAG, msg);
    }
}
