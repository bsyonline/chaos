/*
 * Copyright (C) 2019 bsyonline
 */
package com.rolex.alphax.concurrent.pool;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.LockSupport;

/**
 * @author rolex
 * @since 2019
 */
public class MyFutureTask<T> implements Runnable {

    MyCallable<T> callable;
    T result = null;
    private volatile String state = "NEW";

    LinkedBlockingQueue<Thread> queue = new LinkedBlockingQueue();

    public MyFutureTask(MyCallable<T> callable) {
        this.callable = callable;
    }

    public T get() {
        if (state.equals("FINISH")) {
            return result;
        }

        queue.add(Thread.currentThread());
        while (!state.equals("FINISH")) {
            LockSupport.park();
        }
        return result;
    }

    @Override
    public void run() {
        try {
            result = callable.call();
            state = "FINISH";
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
        Thread t = queue.poll();
        while (t != null) {
            LockSupport.unpark(t);
            t = queue.poll();
        }
    }
}

interface MyCallable<T> {
    <T> T call() throws Exception;
}