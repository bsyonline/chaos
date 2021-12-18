/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.concurrent.cas;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @author rolex
 * @since 2020
 */
public class UnsafeExample {

    private static Unsafe unsafe;
    private static long valueOffset;
    private volatile int value;

    static {
        try {
            Field theUnsafeField = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafeField.setAccessible(true);
            unsafe = (Unsafe) theUnsafeField.get(null);
            valueOffset = unsafe.objectFieldOffset(UnsafeExample.class.getDeclaredField("value"));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        //Unsafe unsafe = Unsafe.getUnsafe();
        System.out.println(unsafe);
        System.out.println(valueOffset);
    }
}
