/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.concurrent.thread;

/**
 * @author rolex
 * @since 2020
 */
public class InterruptExample {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            while (true) {
                System.out.println(Thread.currentThread().isInterrupted());
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("++++++++++++++++++++++++++++++++++++++++++++++is interrupted");
                } else {
                    System.out.println("==============================================not interrupted");
                }

            }
        });
        t1.start();
        Thread.sleep(2000);
        t1.interrupt();
        System.out.println("end");
    }

}
