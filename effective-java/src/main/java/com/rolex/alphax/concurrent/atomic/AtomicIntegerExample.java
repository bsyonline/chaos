/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.concurrent.atomic;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author rolex
 * @since 2020
 */
public class AtomicIntegerExample {

    public static void main(String[] args) throws InterruptedException {
        AtomicInteger atomicInteger = new AtomicInteger();
        new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                atomicInteger.incrementAndGet();
            }
        }).start();
        new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                atomicInteger.incrementAndGet();
            }
        }).start();
        new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                atomicInteger.incrementAndGet();
            }
        }).start();
        Thread.sleep(1000);
        System.out.println(atomicInteger.get());
    }
}
