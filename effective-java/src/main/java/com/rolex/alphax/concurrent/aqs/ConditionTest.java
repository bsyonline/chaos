/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.concurrent.aqs;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author rolex
 * @since 2020
 */
public class ConditionTest {

    public static void main(String[] args) throws InterruptedException {
        ReentrantLock reentrantLock = new ReentrantLock();
        Condition condition = reentrantLock.newCondition();
        Thread t1 = new Thread(() -> {
            reentrantLock.lock();
            try {
                condition.await();
                System.out.println(Thread.currentThread().getName() + " wake up");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                reentrantLock.unlock();
            }
        }, "t1");
        t1.start();
        Thread.sleep(1000);
        Thread t2 = new Thread(() -> {
            reentrantLock.lock();
            try {
                condition.await();
                System.out.println(Thread.currentThread().getName() + " wake up");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                reentrantLock.unlock();
            }
        }, "t2");
        t2.start();
        Thread.sleep(1000);
        new Thread(() -> {
            reentrantLock.lock();
            condition.signalAll();
            reentrantLock.unlock();
        }, "t5").start();
    }
}
