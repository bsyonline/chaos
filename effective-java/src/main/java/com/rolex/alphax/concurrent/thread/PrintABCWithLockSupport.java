package com.rolex.alphax.concurrent.thread;

import java.util.concurrent.locks.LockSupport;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
public class PrintABCWithLockSupport {
    static Thread a, b, c;

    public static void main(String[] args) {

        a = new Thread(() -> {
            while (true) {
                System.out.println("A");
                LockSupport.unpark(b);
                LockSupport.park();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        b = new Thread(() -> {
            while (true) {
                LockSupport.park();
                System.out.println("B");
                LockSupport.unpark(c);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        c = new Thread(() -> {
            while (true) {
                LockSupport.park();
                System.out.println("C");
                LockSupport.unpark(a);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        a.start();
        b.start();
        c.start();
    }
}
