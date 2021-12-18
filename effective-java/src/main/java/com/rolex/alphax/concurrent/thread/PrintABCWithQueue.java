package com.rolex.alphax.concurrent.thread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
public class PrintABCWithQueue {
    static BlockingQueue<Thread> queue = new LinkedBlockingQueue<>(10);

    public static void main(String[] args) throws InterruptedException {
        queue.add(new A("A"));
        queue.add(new A("B"));
        queue.add(new A("C"));
        while (true) {
            A take = (A) queue.take();
            take.print();
            queue.add(take);
            Thread.sleep(1000);
        }
    }

    static class A extends Thread {
        String name;

        public A(String name) {
            this.name = name;
        }

        public void print() {
            System.out.println(name);
        }
    }
}
