package com.bean;

import java.io.Serializable;

public class StudentBean implements Serializable {
    public int age;
    public String name;
    public String sex;

    public StudentBean(int age, String name) {
        this.age = age;
        this.name = name;
    }

    @Override
    public String toString() {
        return "StudentBean{" +
                "age=" + age +
                ", name='" + name + '\'' +
                '}';
    }
//    @Override
//    public String toString() {
//        return "StudentBean{" +
//                "age=" + age +
//                ", name='" + name + '\'' +
//                ", sex='" + sex + '\'' +
//                '}';
//    }
}
