/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.creational.singleton;

/**
 * @author rolex
 * @since 2020
 */
public class Singleton1 {
    private Singleton1() {
    }

    private static final Singleton1 INSTANCE = new Singleton1();

    public static Singleton1 getInstance() {
        return INSTANCE;
    }

}
