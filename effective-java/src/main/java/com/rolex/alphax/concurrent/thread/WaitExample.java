/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.concurrent.thread;

import lombok.SneakyThrows;

import java.time.LocalDateTime;

/**
 * @author rolex
 * @since 2020
 */
public class WaitExample {
    static Object obj = new Object();
    static int count = 0;
    static class T1 implements Runnable{
        @SneakyThrows
        @Override
        public void run() {
            synchronized (obj) {
                while (count++<5) {
                    System.out.println(Thread.currentThread().getName() + "> "+LocalDateTime.now());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                obj.wait();
                //Thread.sleep(5000);
            }
        }
    }
    static class T2 implements Runnable{
        @Override
        public void run() {
            synchronized (obj) {
                while (true) {
                    System.out.println(Thread.currentThread().getName() + "> "+LocalDateTime.now());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new T1());
        Thread t2 = new Thread(new T2());
        t1.start();
        t2.start();
        while (true) {
            System.out.println("t1 state=" + t1.getState() + ", t2 state=" + t2.getState());
            Thread.sleep(1000);
        }
    }

}
