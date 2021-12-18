/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.concurrent;

/**
 * @author rolex
 * @since 2020
 */
public class ProducerAndConsumerExample0 {

    public static void main(String[] args) {
        new Thread(() -> {
            while (true) {
                System.out.println(Thread.currentThread().getName() + "生产了一个苹果");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Thread.yield();
            }
        }, "t1").start();
        new Thread(() -> {
            while (true) {
                System.out.println(Thread.currentThread().getName() + "消费了一个苹果");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Thread.yield();
            }
        }, "t2").start();
    }
}



