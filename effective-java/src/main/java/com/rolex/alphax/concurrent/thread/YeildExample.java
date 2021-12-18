/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.concurrent.thread;

/**
 * @author rolex
 * @since 2020
 */
public class YeildExample {
    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread(() -> {
            try {
                for (int i=0;i<5;i++) {
                    System.out.println(Thread.currentThread().getName() + " is running");
                    Thread.sleep(3000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " finished");
        });
        t1.start();
        /*
         * join 是 t1 执行完才能执行main
         */
        t1.join();

        System.out.println(Thread.currentThread().getName() + " is running");
    }
}
