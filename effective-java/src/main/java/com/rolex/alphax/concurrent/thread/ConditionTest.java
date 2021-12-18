package com.rolex.alphax.concurrent.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionTest {

    public static void main(String[] args) throws InterruptedException {
        ReentrantLock reentrantLock = new ReentrantLock();

        Condition c1 = reentrantLock.newCondition();
        Condition c2 = reentrantLock.newCondition();
        Thread t1 = new Thread(() -> {
            while (true) {
                reentrantLock.lock();
                try {
                    System.out.println(Thread.currentThread().getName() + " wake up");
                    c1.await();
                    c2.signal();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    reentrantLock.unlock();
                }
            }
        }, "t1");

        Thread.sleep(1000);
        Thread t2 = new Thread(() -> {
            while (true) {
                reentrantLock.lock();
                try {
                    System.out.println(Thread.currentThread().getName() + " wake up");
                    c2.await();
                    c1.signal();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    reentrantLock.unlock();
                }
            }
        }, "t2");
        t1.start();
        t2.start();
//        Thread.sleep(1000);
//        new Thread(() -> {
//            reentrantLock.lock();
//            condition.signalAll();
//            reentrantLock.unlock();
//        }, "t5").start();
    }
}