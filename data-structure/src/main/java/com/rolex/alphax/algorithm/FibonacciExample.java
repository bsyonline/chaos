/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.algorithm;

/**
 * @author rolex
 * @since 2020
 */
public class FibonacciExample {

    public static void main(String[] args) {
        int n = 8;
        for (int i = 1; i <= n; i++) {
            int a = fibonacci1(i);
            System.out.print(a + " ");
        }
        System.out.println();
        System.out.println("---");
        for (int i = 1; i <= n; i++) {
            int b = fibonacci2(i);
            System.out.print(b + " ");
        }
    }

    public static int fibonacci1(int n) {
        if (n == 1) {
            return 1;
        }
        if (n == 2) {
            return 1;
        }
        return fibonacci1(n - 1) + fibonacci1(n - 2);
    }

    public static int fibonacci2(int n) {
        if (n == 1) {
            return 1;
        }
        if (n == 2) {
            return 1;
        }
        int a = 1;
        int b = 1;
        int f = 0;
        for (int i = 3; i <= n; i++) {
            f = a + b;
            a = b;
            b = f;
        }
        return f;
    }
}
