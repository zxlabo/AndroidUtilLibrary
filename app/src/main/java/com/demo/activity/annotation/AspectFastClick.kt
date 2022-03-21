package com.demo.activity.annotation

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.aspectj.lang.reflect.MethodSignature

/**
 * 防止快速点击的ApsectJ
 */
@Aspect
class AspectFastClick {

    /**
    @Pointcut：定义切点，标记切点为所有被 @AopOnclick注解的方法
    @com.demo.activity.annotation.AopOnclick：注解筛选，注意：这里@com.demo.activity.annotation 需要替换成 自己项目中AopOnclick这个类的全路径
     *：类路径,*为任意路径
     * 法名,*为任意方法名
     * (..)：方法参数,'..'为任意个任意类型参数
    &&：并集
     */
    @Pointcut("execution(@com.demo.activity.annotation.AopOnclick * *(..))")
    fun methodAnnotated() {
    }

    /**
     * 定义一个切面方法，包裹切点方法
     */
    @Around("methodAnnotated()")
    @Throws(Throwable::class)
    fun aroundJoinPoint(joinPoint: ProceedingJoinPoint) {
        // 取出方法的注解
        val methodSignature = joinPoint.signature as MethodSignature
        val method = methodSignature.method
        if (!method.isAnnotationPresent(AopOnclick::class.java)) {
            return
        }
        val aopOnclick = method.getAnnotation(AopOnclick::class.java)
        // 判断是否快速点击
        if (!AopClickUtil.isFastDoubleClick(aopOnclick.outTime)) {
            // 不是快速点击，执行原方法
            joinPoint.proceed()
        }
    }


}
