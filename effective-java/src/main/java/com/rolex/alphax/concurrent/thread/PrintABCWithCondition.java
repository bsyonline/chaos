package com.rolex.alphax.concurrent.thread;

import java.util.concurrent.locks.Condition;
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
public class PrintABCWithCondition {
    static Lock lock = new ReentrantLock();
    static Condition c1 = lock.newCondition();
    static Condition c2 = lock.newCondition();
    static Condition c3 = lock.newCondition();
    volatile static int count = 0;

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            while (true) {
                lock.lock();
                try {
                    while (count % 3 != 0) {
                        c1.await();
                    }
                    System.out.println("A");
                    c2.signal();
                    count++;
                } catch (InterruptedException e) {
                    e.printStackTrace();
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
                    while (count % 3 != 1) {
                        c2.await();
                    }
                    System.out.println("B");
                    c3.signal();
                    count++;
                } catch (InterruptedException e) {
                    e.printStackTrace();
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
                    while (count % 3 != 2) {
                        c3.await();
                    }
                    System.out.println("C");
                    c1.signal();
                    count++;
                } catch (InterruptedException e) {
                    e.printStackTrace();
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
