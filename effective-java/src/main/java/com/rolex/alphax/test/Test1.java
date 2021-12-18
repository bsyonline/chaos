/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.test;

/**
 * 正常编译执行。
 * 静态方法调用可以通过不用实例化
 *
 * @author rolex
 * @since 2020
 */
public class Test1 {
    private static void testMethod() {
        System.out.println("testMethod");
    }

    public static void main(String[] args) {
        ((Test1) null).testMethod();
    }
}
