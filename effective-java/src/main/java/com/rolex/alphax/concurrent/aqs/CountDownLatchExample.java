/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.concurrent.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * @author rolex
 * @since 2020
 */
@Slf4j
public class CountDownLatchExample {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch doneSignal = new CountDownLatch(6);

        for (int i = 0; i < 5; ++i) // create and start threads
            new Thread(new CountDownLatchExample().new Worker(doneSignal),"t"+i).start();

        doneSignal.await();           // wait for all to finish
        log.info("all work finished");
    }

    class Worker implements Runnable {
        private final CountDownLatch doneSignal;

        Worker(CountDownLatch doneSignal) {
            this.doneSignal = doneSignal;
        }

        public void run() {
            doWork();
            doneSignal.countDown();
        }

        void doWork() {
            try {
                Thread.sleep(new Random().nextInt(5) * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("{} 任务完成", Thread.currentThread().getName());
        }
    }
}

