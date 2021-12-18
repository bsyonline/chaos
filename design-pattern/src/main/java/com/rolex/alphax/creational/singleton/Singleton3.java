/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.creational.singleton;

/**
 * @author rolex
 * @since 2020
 */
public class Singleton3 {
    private Singleton3() {
    }

    static class SingletonHolder {
        private static final Singleton3 INSTANCE = new Singleton3();
    }

    public static Singleton3 getInstance() {
        return SingletonHolder.INSTANCE;
    }
}
