package com.rolex.alphax.concurrent.thread;

import java.util.concurrent.Semaphore;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
public class PrintABCWithSemaphore {
    static Semaphore a = new Semaphore(1);
    static Semaphore b = new Semaphore(0);
    static Semaphore c = new Semaphore(0);

    public static void main(String[] args) {
        new Thread(() -> {
            while (true) {
                try {
                    a.acquire();
                    System.out.println("A");
                    b.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
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
                try {
                    b.acquire();
                    System.out.println("B");
                    c.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
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
                try {
                    c.acquire();
                    System.out.println("C");
                    a.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
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
