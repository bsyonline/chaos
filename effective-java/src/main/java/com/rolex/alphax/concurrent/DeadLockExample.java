/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.concurrent;

/**
 * @author rolex
 * @since 2020
 */
public class DeadLockExample {

    public static void main(String[] args) {
        Worker worker = new DeadLockExample().new Worker();
        new Thread(() -> {
            worker.foo();
        }).start();
        new Thread(() -> {
            worker.bar();
        }).start();
    }

    class Worker {

        Object lock1 = new Object();
        Object lock2 = new Object();


        public void foo() {
            synchronized (lock1) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (lock2) {
                }
            }
        }

        public void bar() {
            synchronized (lock2) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (lock1) {
                }
            }
        }
    }
}
