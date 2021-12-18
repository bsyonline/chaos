/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.jvm;

/**
 * -XX:+TraceClassLoading
 *
 * @author rolex
 * @since 2020
 */
public class InitializationExample {

    public static void main(String[] args) {
        /*
            静态属性的调用：直接定义属性的类才会被初始化
            会加载子类但不会初始化子类
         */
        System.out.println(SubClass.value);
        /*
            不会初始化
         */
        SuperClass[] sca = new SuperClass[10];
        /*
            常量引用不会触发初始化
            常量在编译阶段就放到了InitializationExample的常量池中，所以这个是对常量池的引用，不是对ConstClass的引用
         */
        System.out.println(ConstClass.HELLO_WORLD);
    }

}

class SuperClass {
    static {
        System.out.println("SuperClass init");
    }

    public static int value = 123;
}

class SubClass extends SuperClass {
    static {
        System.out.println("SubClass init");
    }
}

class ConstClass {
    static {
        System.out.println("ConstClass init");
    }

    public static final String HELLO_WORLD = "hello world";
}