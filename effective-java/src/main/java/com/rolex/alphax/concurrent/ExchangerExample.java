/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.concurrent;

import java.util.concurrent.Exchanger;

/**
 * @author rolex
 * @since 2020
 */
public class ExchangerExample {
    public static void main(String[] args) {
        Exchanger exchanger = new Exchanger();
        new Thread(() -> {
            int count = 0;
            for (int i = 0; i < 1000; i++) {
                count++;
            }
            try {
                // 将 t1 的结果给 t2
                System.out.println(Thread.currentThread().getName() + "->" + exchanger.exchange(count));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t1").start();
        new Thread(() -> {
            int count = 0;
            for (int i = 0; i < 2000; i++) {
                count++;
            }
            try {
                // 将 t2 的结果给 t1
                System.out.println(Thread.currentThread().getName() + "->" + exchanger.exchange(count));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t2").start();
    }
}
