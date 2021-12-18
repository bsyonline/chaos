/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.concurrent;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author rolex
 * @since 2020
 */
public class ProducerAndConsumerExample3 {

    public static void main(String[] args) {
        LinkedBlockingQueue queue = new LinkedBlockingQueue<>(10);

        new Thread(new ProducerAndConsumerExample3().new Consumer(queue)).start();
        new Thread(new ProducerAndConsumerExample3().new Producer(queue)).start();
    }

    class Producer implements Runnable {

        LinkedBlockingQueue queue;

        public Producer(LinkedBlockingQueue queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    queue.put(1);
                    System.out.println("put element, queue size = " + queue.size());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class Consumer implements Runnable {

        LinkedBlockingQueue queue;

        public Consumer(LinkedBlockingQueue queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    queue.take();
                    System.out.println("take element, queue size = " + queue.size());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}



