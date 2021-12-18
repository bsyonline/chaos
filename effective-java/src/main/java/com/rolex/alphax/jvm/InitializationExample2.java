/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.jvm;

/**
 * @author rolex
 * @since 2020
 */
public class InitializationExample2 {

    static class Parent {
        public static int A = 1;

        static {
            A = 2;
        }
    }

    static class Sub extends Parent {
        public static int B = A;
    }

    public static void main(String[] args) {
        /*
            父类的 static 先于子类的 static 执行
         */
        System.out.println(Sub.B);
    }
}

