/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.concurrent.aqs;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author rolex
 * @since 2020
 */
public class ReentrantLockExample {
    public static void main(String[] args) throws InterruptedException {
        ReentrantLock lock = new ReentrantLock(true);
        ReentrantLockExample example = new ReentrantLockExample();
        new Thread(() -> {
            try {
                lock.lock();
                work();
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }, "t1").start();

        new Thread(() -> {
            try {
                lock.lock();
                work();
            } finally {
                lock.unlock();
            }
        }, "t2").start();

        new Thread(() -> {
            try {
                lock.lock();
                work();
            } finally {
                lock.unlock();
            }
        }, "t3").start();

    }

    public static void work() {
        System.out.println("do something");
//            try {
//                Thread.sleep(Integer.MAX_VALUE);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
    }
}
