package com.rolex.alphax.concurrent;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class ProducerCustomer {
    //定义最大尺寸
    private final int MAX_SIZE = 100;
    //定义队列
    private final Queue<Integer> queue = new LinkedList<Integer>();

    public static void main(String[] args) {
        ProducerCustomer producerCustomer = new ProducerCustomer();
        Producer producer = producerCustomer.new Producer();
        Customer customer = producerCustomer.new Customer();
        new Thread(producer).start();
        new Thread(customer).start();


    }

    /**
     * 生产者
     */
    class Producer implements Runnable {

        @Override
        public void run() {
            while (true) {
                //lock
                synchronized (queue) {
                    if (queue.size() < MAX_SIZE) {
                        //生产者生产
                        int num = new Random().nextInt(100);
                        //将生产的东西放到队列中
                        boolean offerResult = queue.offer(num);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        //生产完毕 唤醒消费者
                        queue.notifyAll();
                        //输出生产者信息
                        System.out.println("生产者：" + Thread.currentThread().getName() + "生产了 " + num + "产品容量" + queue.size());
                    } else {
                        //队列已满 生产者等待
                        try {
                            queue.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    /**
     * 消费者
     */
    class Customer implements Runnable {

        @Override
        public void run() {
            while (true) {
                synchronized (queue) {
                    if (queue.size() > 0) {
                        //消费者消费数据
                        int num = queue.poll();
                        System.out.println("消费者 " + Thread.currentThread().getName() + "消费了产品" + num + "产品容量" + queue.size());
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        //唤醒生产者
                        queue.notifyAll();
                    } else {
                        try {
                            queue.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

}