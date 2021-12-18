/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.test;

/**
 * @author rolex
 * @since 2020
 */
public class Test2 {
    public void add(byte b){
        b = b++;
    }
    public void test(){
        Byte a = 127;
        Byte b = 127;
        add(++a);
        System.out.print(a + " ");
        add(b);
        System.out.println(b + "");
    }
    public static void main(String[] args) {
        new Test2().test();
    }
}
