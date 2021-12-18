/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.concurrent.aqs;

import java.util.HashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author rolex
 * @since 2020
 */
public class ReadWriteLockExample {

    public static void main(String[] args) {
        Cache cache = new ReadWriteLockExample().new Cache();
        for (int i = 0; i < 5; i++) {
            int finalI = i;
            new Thread(() -> {
                cache.put("key-" + finalI, finalI);
            }).start();
        }

        for (int i = 0; i < 5; i++) {
            int finalI = i;
            new Thread(() -> {
                System.out.println(cache.get("key-" + finalI));
            }).start();
        }
    }

    class Cache {
        volatile HashMap map = new HashMap();
        ReadWriteLock lock = new ReentrantReadWriteLock();

        public void put(String key, Object val) {
            lock.writeLock().lock();
            try {
                System.out.println(Thread.currentThread().getName() + " write begin");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                map.put(key, val);
                System.out.println(Thread.currentThread().getName() + " write finish");
            } finally {
                lock.writeLock().unlock();
            }
        }

        public Object get(String key) {
            lock.readLock().lock();
            try {
                System.out.println(Thread.currentThread().getName() + " read begin");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Object obj = map.get(key);
                System.out.println(Thread.currentThread().getName() + " read finish");
                return obj;
            } finally {
                lock.readLock().unlock();
            }
        }
    }
}
