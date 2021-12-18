/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.concurrent.aqs;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * A B C 3 个线程按顺序打印 5 次
 *
 * @author rolex
 * @since 2020
 */
public class ConditionExample {
    public static void main(String[] args) {
        Printer printer = new ConditionExample().new Printer();
        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                printer.print1();
            }
        }, "A").start();
        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                printer.print2();
            }
        }, "B").start();
        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                printer.print3();
            }
        }, "C").start();
    }

    class Printer {
        volatile int count = 0;
        Lock lock = new ReentrantLock();
        Condition c1 = lock.newCondition();
        Condition c2 = lock.newCondition();
        Condition c3 = lock.newCondition();

        public void print1() {
            lock.lock();
            try {
                while (count % 3 != 0) {
                    c1.await();
                }
                System.out.println(Thread.currentThread().getName() + " print " + count);
                count++;
                c2.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

        public void print2() {
            lock.lock();
            try {
                while (count % 3 != 1) {
                    c2.await();
                }
                System.out.println(Thread.currentThread().getName() + " print " + count);
                count++;
                c3.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

        public void print3() {
            lock.lock();
            try {
                while (count % 3 != 2) {
                    c3.await();
                }
                System.out.println(Thread.currentThread().getName() + " print " + count);
                count++;
                c1.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

    }
}
