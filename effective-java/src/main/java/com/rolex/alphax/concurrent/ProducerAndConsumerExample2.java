/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.concurrent;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author rolex
 * @since 2020
 */
public class ProducerAndConsumerExample2 {

    public static void main(String[] args) {
        Queue queue = new ProducerAndConsumerExample2().new Queue();

        new Thread(new ProducerAndConsumerExample2().new Consumer(queue)).start();
        new Thread(new ProducerAndConsumerExample2().new Producer(queue)).start();
    }

    class Producer implements Runnable {

        Queue queue;

        public Producer(Queue queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            while (true) {
                queue.add();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class Consumer implements Runnable {

        Queue queue;

        public Consumer(Queue queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            while (true) {
                queue.get();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class Queue {
        LinkedList queue = new LinkedList();
        Lock lock = new ReentrantLock();
        Condition c = lock.newCondition();

        public void add() {
            try {
                lock.lock();
                while (queue.size() == 10) {
                    try {
                        c.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                queue.add(1);
                System.out.println("add element, queue size=" + queue.size());
                c.signalAll();
            } finally {
                lock.unlock();
            }

        }

        public void get() {
            try {
                lock.lock();
                while (queue.size() == 0) {
                    try {
                        c.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                queue.poll();
                System.out.println("poll element, queue size=" + queue.size());
                c.signalAll();
            } finally {
                lock.unlock();
            }
        }
    }
}



