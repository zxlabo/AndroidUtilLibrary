//package com.apple.dance.annotation;
//
//import android.util.Log;
//
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.Signature;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//
//@Aspect
//public class AspectRecordTime {
//    /**
//     * 统计Application方法的耗时
//     */
//    @Around("execution (* com.apple.dance.MyApplication.**(..))")
//    public void recordApplicationTime(ProceedingJoinPoint joinPoint) {
//        Signature signature = joinPoint.getSignature();
//        String name = signature.toShortString();
//        long time = System.currentTimeMillis();
//        try {
//            //方法执行之前
//            Log.e("=====", name + "Before： cost" + (System.currentTimeMillis() - time));
//            joinPoint.proceed();
//            //方法执行之后
//            Log.e("=====", name + "After cost" + (System.currentTimeMillis() - time));
//        } catch (Throwable throwable) {
//            throwable.printStackTrace();
//        }
//    }
//
//
//    /**
//     * 统计Activity以on开头的方法的耗时
//     */
////    @Around("execution(* android.app.Activity.on**(..))")
////    public void recordActivityTime(ProceedingJoinPoint joinPoint) {
////        try {
////            Signature signature = joinPoint.getSignature();
////            String name = signature.toShortString();
////            long time = System.currentTimeMillis();
////            //方法执行之前
////            Log.e("=====", name + "Before： cost" + (System.currentTimeMillis() - time));
////            joinPoint.proceed();
////            //方法执行之后
////            Log.e("=====", name + "After cost" + (System.currentTimeMillis() - time));
////        } catch (Throwable throwable) {
////            throwable.printStackTrace();
////        }
////    }
//
//}
