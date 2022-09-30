package com.apple.dance.fragment;

import java.io.Serializable;

/**
 * 原因
 * 1、Person实现了Serializable接口。
 * 2、Student没有实现Serializable接口。
 */
public class Person implements Serializable {
    public String name;
    public Student student;
}
