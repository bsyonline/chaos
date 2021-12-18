/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.concurrent.pool;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author rolex
 * @since 2020
 */
public class FutureExample {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long start = System.currentTimeMillis();
        FutureTask<Integer> f1 = new FutureTask((Callable<Integer>) () -> {
            Thread.sleep(2000);
            return 10;
        });
        FutureTask<Integer> f2 = new FutureTask((Callable<Integer>) () -> {
            Thread.sleep(3000000);
            return 20;
        });
        FutureTask<Integer> f3 = new FutureTask((Callable<Integer>) () -> {
            Thread.sleep(500000);
            return 30;
        });
        new Thread(f1).start();
        new Thread(f2).start();
        new Thread(f3).start();
        foo();
        f3.cancel(true);
        System.out.println("--");
        while (!f1.isDone() || !f2.isDone()) {
            System.out.println("f1" + (f1.isDone() ? "完成，" : "未完成，") + "f2" + (f2.isDone() ? "完成" : "未完成"));
            Thread.sleep(300);
        }
        System.out.println("f1" + (f1.isDone() ? "完成，" : "未完成，") + "f2" + (f2.isDone() ? "完成" : "未完成"));
        int result = f2.get() + f3.get();
        System.out.println("f3 cancelled state: "+f3.isCancelled());
        System.out.println("result=" + result + ", costs " + (System.currentTimeMillis() - start) + "ms");
    }

    static void foo() throws InterruptedException {
        System.out.println("同步执行foo");
        Thread.sleep(1000);
        System.out.println("foo done");
    }
}
