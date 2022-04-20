package com.demo.activity.annotation;

import android.util.Log;
import android.view.View;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.lang.reflect.Method;

/**
 * 防止快速点击AspectJ
 *
 * @author hehe
 */
@Aspect
public class AvoidFastClick {

    private static final long DURATION_TIME = 5000L;

    private static final String ON_CLICK_POINT = "call(* android.view.View.setOnClickListener(android.view.View.OnClickListener))";

    @Pointcut(ON_CLICK_POINT)

    public void onClickPointcuts() {

    }

    @Around("onClickPointcuts()")
    public void clickFilterHook(ProceedingJoinPoint joinPoint) {
        try {
            Object obj = joinPoint.getTarget();
            if (obj instanceof View) {
                View view = (View) obj;
                Object[] objArr = joinPoint.getArgs();
                if (objArr != null && objArr.length == 1) {
                    Object para1 = objArr[0];
                    if (para1 instanceof View.OnClickListener) {
                        Method method = View.class.getMethod("setOnClickListener", View.OnClickListener.class);
                        method.invoke(view, (View.OnClickListener) v -> {
                            Log.e("====", "点击了");
                            // 判断是否快速点击
                            if (!AopClickUtil.INSTANCE.isFastDoubleClick(DURATION_TIME)) {
                                // 不是快速点击，执行原方法
                                ((View.OnClickListener) para1).onClick(view);
                            }
                        });
                    }
                }
            }
        } catch (Exception e) {
            Log.d("clickAspect", e.toString());
        }
    }

}
