package com.apple.dance.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zhouxin
 */
@Target(ElementType.FIELD) //类成员（构造方法、方法、成员变量）
@Retention(RetentionPolicy.RUNTIME) //表示运行时保留的注解，给反射用
public @interface AutoWired {

}