/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.collection;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author rolex
 * @since 2020
 */
public class LRUCacheExample {
    public static void main(String[] args) {
        LRUCache cache = new LRUCache(10);
        for (int i = 0; i < 10; i++) {
            cache.put(i, i);
        }
        cache.get(5);
        for (int i = 10; i < 15; i++) {
            cache.put(i, i);
        }
        System.out.println(cache.get(1));
        System.out.println(cache.get(5));
        System.out.println(cache.get(10));
    }
}

class LRUCache {
    int cacheSize;
    Map<Integer, Integer> map;

    public LRUCache(int cacheSize) {
        this.cacheSize = cacheSize;
        map = Collections.synchronizedMap(new LinkedHashMap<Integer, Integer>(cacheSize, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
                return size() > cacheSize;
            }
        });
    }

    public int get(int key) {
        try {
            return map.get(key);
        } catch (NullPointerException e) {
            return -1;
        }
    }

    public void put(int key, int value) {
        map.put(key, value);
    }
}