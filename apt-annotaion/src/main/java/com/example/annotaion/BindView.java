package com.example.annotaion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zhouxin
 * 创建注解类：BindView
 */
//编译时注解
@Retention(RetentionPolicy.SOURCE)
//类成员（构造方法、方法、成员变量）
@Target(ElementType.FIELD)
public @interface BindView {
    //用于获取对应View的id。
    int value();
}
