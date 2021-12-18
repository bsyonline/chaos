/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.concurrent;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author rolex
 * @since 2020
 */
public class DeadLockExample1 {

    public static void main(String[] args) {
        Worker t1 = new DeadLockExample1().new Worker();
        Worker t2 =new DeadLockExample1().new Worker();
        new Thread(t1).start();
        new Thread(t2).start();
    }

    class Worker implements Runnable{

        ReentrantLock lock = new ReentrantLock();
        Condition condition = lock.newCondition();

        @Override
        public void run() {
            lock.lock();
            try{
                condition.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }
}
