/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.creational.singleton;

/**
 * @author rolex
 * @since 2020
 */
public class Singleton2 {
    private Singleton2() {
    }

    public volatile static Singleton2 instance;

    public static Singleton2 getInstance() {
        if (instance == null) {
            synchronized (Singleton2.class) {
                if (instance == null) {
                    instance = new Singleton2();
                }
            }
        }
        return instance;
    }
}
