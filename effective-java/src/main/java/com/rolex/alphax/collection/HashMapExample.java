/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.collection;


import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author rolex
 * @since 2020
 */
public class HashMapExample {

    public static void main(String[] args) {
        HashMap<Key, Integer> map = new HashMap();
        for (int i = 0; i < 11; i++) {
            map.put(new Key(i), i);
        }

        map.get(new Key(8));

        for (int i = 8; i >= 0; i--) {
            map.remove(new Key(i));
        }

        HashMap<Key, Integer> map1 = new HashMap(100);
        for (int i = 0; i < 11; i++) {
            map1.put(new Key(i), i);
        }

        Hashtable hashtable = new Hashtable();
        Collections.synchronizedMap(new HashMap<>());
        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
    }

    static final int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }
}

class Key implements Comparator<Key> {
    int id;

    public Key(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int compare(Key o1, Key o2) {
        return o1.id - o2.id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Key user = (Key) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
