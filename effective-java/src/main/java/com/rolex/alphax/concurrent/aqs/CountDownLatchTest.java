/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.concurrent.aqs;

import java.util.concurrent.CountDownLatch;

/**
 * @author rolex
 * @since 2020
 */
public class CountDownLatchTest {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(2);
//        new Thread(() -> {
//            System.out.println(Thread.currentThread().getName() + " task done");
//            latch.countDown();
//        }, "t1").start();
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " task done");
            latch.countDown();
        }, "t2").start();
//        }
        latch.await();
        System.out.println("all thread wake up");
    }
}
