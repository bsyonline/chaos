/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.asm05;

/**
 * @author rolex
 * @since 2020
 */
public class Logger {

    public static void info(String name, int... parameters) {
        System.out.println("方法：" + name);
        System.out.println("参数：" + "[" + parameters[0] + "," + parameters[1] + "]");
    }

}