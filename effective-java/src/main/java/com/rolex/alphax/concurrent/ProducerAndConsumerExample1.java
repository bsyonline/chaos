/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.concurrent;

import java.util.LinkedList;

/**
 * @author rolex
 * @since 2020
 */
public class ProducerAndConsumerExample1 {

    public static void main(String[] args) {
        Queue queue = new ProducerAndConsumerExample1().new Queue();

        new Thread(new ProducerAndConsumerExample1().new Consumer(queue)).start();
        new Thread(new ProducerAndConsumerExample1().new Producer(queue)).start();
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

        public void add() {
            synchronized (this) {
                while (queue.size() == 10) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                queue.add(1);
                System.out.println("add element, queue size=" + queue.size());
                notifyAll();
            }
        }

        public void get() {
            synchronized (this) {
                while (queue.size() == 0) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                queue.poll();
                System.out.println("poll element, queue size=" + queue.size());
                notifyAll();
            }
        }
    }

}



