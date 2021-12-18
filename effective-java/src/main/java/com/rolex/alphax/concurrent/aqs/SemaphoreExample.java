/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.concurrent.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 * @author rolex
 * @since 2020
 */
@Slf4j
public class SemaphoreExample {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(5);
        for (int i = 0; i < 10; i++) {
            new Thread(new SemaphoreExample().new Worker(semaphore)).start();
        }
    }

    class Worker implements Runnable {

        Semaphore semaphore;

        public Worker(Semaphore semaphore) {
            this.semaphore = semaphore;
        }

        @Override
        public void run() {
            try {
                semaphore.acquire();
                log.info("{} 占用", Thread.currentThread().getName());
                Thread.sleep(new Random().nextInt(5) * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                log.info("{} 释放", Thread.currentThread().getName());
                semaphore.release();
            }
        }
    }
}


