/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.concurrent.aqs;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.LockSupport;

/**
 * @author rolex
 * @since 2020
 */
public class MyAQS {

    public volatile int i;
    volatile int state = 0;
    ConcurrentLinkedQueue<Thread> queue = new ConcurrentLinkedQueue();
    static Unsafe unsafe;
    static long offset;
    Thread executeThread;

    static {
        try {
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            unsafe = (Unsafe) theUnsafe.get(null);
            offset = unsafe.objectFieldOffset(MyAQS.class.getDeclaredField("state"));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public Thread getExecuteThread() {
        return executeThread;
    }

    public void setExecuteThread(Thread executeThread) {
        this.executeThread = executeThread;
    }

    public boolean acquire() {
        Thread current = Thread.currentThread();
        int s = getState();
        if (s == 0) {
            if ((queue.size() == 0 || current == queue.peek()) && compareAndSwap(0, 1)) {
                setExecuteThread(current);
                return true;
            }
        }
        return false;
    }

    private boolean compareAndSwap(int i, int i2) {
        return unsafe.compareAndSwapInt(this, offset, i, i2);
    }


    public void add() {
        lock();
        i++;
        unlock();
    }

    private void unlock() {
        state = getState();

        if (state == 1 && getExecuteThread() == Thread.currentThread() && compareAndSwap(1, 0)) {
            setExecuteThread(null);
            Thread t = queue.peek();
            if (t != null) {
                LockSupport.unpark(t);
            }
        }
    }

    private void lock() {
        if (acquire()) {
            return;
        }
        Thread current = Thread.currentThread();
        queue.add(current);

        for (; ; ) {
            if (queue.peek() == current && acquire()) {
                queue.poll();
                break;
            }
            LockSupport.park();
        }
    }


    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public ConcurrentLinkedQueue getQueue() {
        return queue;
    }

    public void setQueue(ConcurrentLinkedQueue queue) {
        this.queue = queue;
    }


    public static void main(String[] args) throws InterruptedException {
        MyAQS myAqs = new MyAQS();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    myAqs.add();
                }
            }).start();
        }
        Thread.sleep(1000);
        System.out.println(myAqs.i);
    }
}
