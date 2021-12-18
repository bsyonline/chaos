/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.concurrent;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author rolex
 * @since 2020
 */
public class ConditionExample {
    static Lock lock = new ReentrantLock();
    static Condition c1 = lock.newCondition();
    static Condition c2 = lock.newCondition();
    static Condition c3 = lock.newCondition();

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            try {
                lock.lock();
                try {
                    System.out.println("t1 start");
                    c1.await();
                    System.out.println("t1 end");
                } finally {
                    lock.unlock();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t1").start();
        Thread.sleep(1000);
        new Thread(() -> {
            try {
                lock.lock();
                try {
                    System.out.println("t2 start");
                    c2.await();
                    System.out.println("t2 end");
                } finally {
                    lock.unlock();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t1").start();
        Thread.sleep(1000);
        System.out.println("main");
        lock.lock();
        try {
            c2.signal();
            Thread.sleep(1000);
            c1.signal();
        } finally {
            lock.unlock();
        }
    }
}
