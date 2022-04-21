package com.apple.dance.annotation

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class AopOnclick(val outTime: Long = 5000)
