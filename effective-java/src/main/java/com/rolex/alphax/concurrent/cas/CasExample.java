/*
 * Copyright (C) 2019 bsyonline
 */
package com.rolex.alphax.concurrent.cas;

import sun.misc.Unsafe;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author rolex
 * @since 2019
 */
public class CasExample {

    AtomicInteger atomicInteger = new AtomicInteger();
    static Unsafe unsafe;
    static int offset;
    int i = 0;
    volatile int m = 0;
    volatile int n = 0;
    volatile int k = 0;
    volatile int p = 0;

    static {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe) field.get(null);
            // 直接操作内存地址，需要获取地址偏移量
            offset = (int) unsafe.objectFieldOffset(CasExample.class.getDeclaredField("m"));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 没有同步的 add 方法
     */
    public void add() {
        i++;
    }

    /**
     * 使用 cas 的 add 方法
     */
    public void add1() {
        int current;
        do {
            current = m;
        } while (!unsafe.compareAndSwapInt(this, offset, current, current + 1));
    }

    /**
     * 使用 atomicInteger 的 add 方法
     */
    public void add2() {
        n = atomicInteger.incrementAndGet();
    }

    /**
     * 使用 synchronized 的 add 方法
     */
    public synchronized void add3() {
        k++;
    }

    ReentrantLock reentrantLock = new ReentrantLock();

    /**
     * 使用 reentrantLock 的 add 方法
     */
    public void add4() {
        reentrantLock.lock();
        try {
            p++;
        } finally {
            reentrantLock.unlock();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        CasExample cas = new CasExample();
        for (int i = 0; i < 2; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 1000; j++) {
                        cas.add();
                        cas.add1();
                        cas.add2();
                        cas.add3();
                        cas.add4();
                    }
                }
            }).start();
        }
        Thread.sleep(1000);
        System.out.println("i=" + cas.i);
        System.out.println("m=" + cas.m);
        System.out.println("n=" + cas.n);
        System.out.println("k=" + cas.k);
        System.out.println("p=" + cas.p);
    }

}
