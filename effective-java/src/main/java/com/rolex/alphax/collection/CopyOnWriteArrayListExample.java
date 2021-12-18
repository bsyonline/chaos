/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.collection;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author rolex
 * @since 2020
 */
public class CopyOnWriteArrayListExample {
    /*
        CopyOnWriteArrayList 删除加锁，读不加锁
     */
    public static void main(String[] args) {
        CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<>();
        for (int i = 0; i < 1000; i++) {
            list.add(i);
        }

        new Thread(() -> {
            while (list.size() > 0) {
                Integer remove = list.remove(list.size() - 1);
            }
        }).start();

        new Thread(() -> {
            while (list.size() > 0) {
                Integer integer = list.get(list.size() - 1);
            }
        }).start();

    }
}
