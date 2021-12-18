/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.exception;

/**
 * @author rolex
 * @since 2020
 */
public class TryCatchFinallyExample {
    public static void main(String[] args) {
        TryCatchFinallyExample et = new TryCatchFinallyExample();
        System.out.println(et.test());
    }

    public int test() {
        try {
            int i = 1 / 0;
        } catch (Exception e) {
            System.out.println("catch");
            return 1;
        } finally {
            System.out.println("finally");
//            return 2; // 执行完 finally 就 return 了
        }
        return 3;
    }

}
