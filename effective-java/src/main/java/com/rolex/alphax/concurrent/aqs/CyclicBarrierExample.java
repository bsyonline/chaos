/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.concurrent.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author rolex
 * @since 2020
 */
@Slf4j
public class CyclicBarrierExample {
    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(5, () -> {
            log.info("所有线程都准备好了");
        });
        for (int i = 0; i < 5; i++) {
            new Thread(new CyclicBarrierExample().new Worker(cyclicBarrier)).start();
        }
    }

    class Worker implements Runnable {

        CyclicBarrier cyclicBarrier;

        public Worker(CyclicBarrier cyclicBarrier) {
            this.cyclicBarrier = cyclicBarrier;
        }

        @Override
        public void run() {
            try {
                doWork();
                cyclicBarrier.await();
                log.info("{} await 解除", Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }

        void doWork() {
            try {
                Thread.sleep(new Random().nextInt(5) * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("{} 准备好了", Thread.currentThread().getName());
        }
    }
}