package com.apple.dance.annotation;

import android.view.View;
import android.view.View.OnClickListener;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.lang.reflect.Method;

/**
 * 防止快速点击
 *
 * @author zhouxin
 */

@Aspect
public class AvoidFastClick {

    private static final String ON_CLICK_POINT = "call(* android.view.View.setOnClickListener(android.view.View.OnClickListener))";

    private static final long DURATION_TIME = 1500L;

    @Pointcut(ON_CLICK_POINT)
    public void onClickPointcuts() {
        //onClickPointcuts
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
                    if (para1 instanceof OnClickListener) {
                        Method method = View.class.getMethod("setOnClickListener", View.OnClickListener.class);
                        method.invoke(view, new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (!AopClickUtil.INSTANCE.isFastDoubleClick(DURATION_TIME)) {
                                    ((OnClickListener) para1).onClick(view);
                                }
                            }
                        });
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
