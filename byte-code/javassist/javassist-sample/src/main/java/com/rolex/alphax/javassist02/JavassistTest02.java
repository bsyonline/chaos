/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.javassist02;

/**
 * @author rolex
 * @since 2020
 */
public class JavassistTest02 {
    private static final int a = 10;

    public int plus(int i, int j) {
        return a + i + j;
    }

    public double divide(int i, int j) {
        return i / j;
    }
}
