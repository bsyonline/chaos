/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.concurrent;

import java.util.concurrent.locks.LockSupport;

/**
 * @author rolex
 * @since 2020
 */
public class LockSupportExample {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            LockSupport.park();
            LockSupport.park(); // unpark 后才能执行
            LockSupport.park(); // interrupted 后才能执行
        });
        t1.start();
        Thread.sleep(3000);
        LockSupport.unpark(t1); // 唤醒第一次 park
        Thread.sleep(3000);
        Thread.interrupted(); // 唤醒第二次 park
        LockSupport.unpark(t1);
    }
}
