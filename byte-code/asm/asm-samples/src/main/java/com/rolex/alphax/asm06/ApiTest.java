/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.asm06;

/**
 * @author rolex
 * @since 2020
 */
public class ApiTest {
    public static void main(String[] args) throws InterruptedException {
        ApiTest apiTest = new ApiTest();
        String res01 = apiTest.hello(111, 17);
        System.out.println("测试结果：" + res01 + "\r\n");
    }

    public String hello(int uId, int age) throws InterruptedException {
        return "Hello World";
    }
}