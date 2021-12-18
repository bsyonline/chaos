/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.concurrent;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author rolex
 * @since 2020
 */
public class LinkedBlockingQueueExample {
    public static void main(String[] args) throws InterruptedException {
        LinkedBlockingQueue<Integer> blockingQueue = new LinkedBlockingQueue<>(3);
        blockingQueue.put(1);
        blockingQueue.put(2);
        blockingQueue.put(3);
        blockingQueue.put(4);
//        boolean flag1 = blockingQueue.add(1);
//        System.out.println("添加元素：" + flag1);
//        System.out.println(blockingQueue.size());
//        list(blockingQueue);
//
//        boolean flag2 = blockingQueue.offer(2);
//        System.out.println("添加元素：" + flag2);
//        boolean flag3 = blockingQueue.offer(3);
//        System.out.println("添加元素：" + flag3);
//        boolean flag4 = blockingQueue.offer(4);
//        System.out.println("添加元素：" + flag4);
//        System.out.println(blockingQueue.size());
//        list(blockingQueue);

        /*try {
            blockingDeque.put(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(blockingDeque.size());
        list(blockingDeque);*/

//        Integer i = blockingQueue.peek();
//        System.out.println("检查元素：" + i);
//        System.out.println(blockingQueue.size());
//        list(blockingQueue);
//
//        Integer e = blockingQueue.poll();
//        System.out.println("取出元素：" + e);
//        System.out.println(blockingQueue.size());
//        list(blockingQueue);
//
//        blockingQueue.poll();
//        blockingQueue.poll();
//        Integer e1 = blockingQueue.poll();
//        System.out.println("取出元素：" + e1);
//        System.out.println(blockingQueue.size());
//        list(blockingQueue);
    }

    public static void list(BlockingQueue queue) {
        System.out.print("queue的元素： ");
        queue.forEach(e -> {
            System.out.print(e + " ");
        });
        System.out.println();
    }
}
