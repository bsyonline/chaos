/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.concurrent.atomic;

import java.util.concurrent.atomic.LongAdder;

/**
 * @author rolex
 * @since 2020
 */
public class LongAdderExample {
    public static void main(String[] args) throws InterruptedException {
        LongAdder longAdder = new LongAdder();
        new Thread(()->{
            for(int i =0;i<100;i++) {
                longAdder.increment();
            }
        }).start();
        new Thread(()->{
            for(int i =0;i<100;i++) {
                longAdder.increment();
            }
        }).start();
        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                longAdder.increment();
            }
        }).start();
        Thread.sleep(1000);
        System.out.println(longAdder.longValue());
    }
}
