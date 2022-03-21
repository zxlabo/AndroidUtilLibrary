package com.demo.activity.annotation

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class AopOnclick(val outTime: Long = 5000)
