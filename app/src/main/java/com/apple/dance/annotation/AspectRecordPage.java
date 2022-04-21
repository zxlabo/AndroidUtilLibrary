package com.apple.dance.annotation;

import android.app.Application;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;

/**
 * @author zhouxin
 * 监听到某个方法执行之后，调用 application的registerActivityLifecycleCallbacks方法。
 */
@Aspect
public class AspectRecordPage {

    /**
     * 监听 AspectInitUtils的init方法的调用，需要传入application参数。
     * 当监听到的时候，会调用 PageExposure.init((Application) obj);方法进行初始化；
     */
    private static final String POINT_CUT = "execution(* com.apple.dance.annotation.AspectInitUtils.init(..))";

    @After(POINT_CUT)
    public void afterLogReportInit(JoinPoint joinPoint) {
        try {
            Object[] parameterValues = joinPoint.getArgs();
            for (Object obj : parameterValues) {
                if (obj instanceof Application) {
                    PageExposure.init((Application) obj);
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}