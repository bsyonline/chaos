package com.rolex.alphax.concurrent.thread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
public class PrintABCWithLock {
    static Lock lock = new ReentrantLock();
    static volatile int count = 0;

    public static void main(String[] args) {
        new Thread(() -> {
            while (true) {
                lock.lock();
                try {
                    while (count % 3 == 0) {
                        System.out.println("A");
                        count++;
                    }
                } finally {
                    lock.unlock();
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread(() -> {
            while (true) {
                lock.lock();
                try {
                    while (count % 3 == 1) {
                        System.out.println("B");
                        count++;
                    }
                } finally {
                    lock.unlock();
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread(() -> {
            while (true) {
                lock.lock();
                try {
                    while (count % 3 == 2) {
                        System.out.println("C");
                        count++;
                    }

                } finally {
                    lock.unlock();
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
