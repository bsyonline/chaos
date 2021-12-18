/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.jvm;

/**
 *
 * @author rolex
 * @since 2020
 */
public class InitializationExample1 {
    static {
        i = 0;
        /*
            程序按顺序执行，可以复制，不能访问，因为 i 还没声明
         */
//        System.out.print(i); // 非法向前引用
    }

    static int i = 1;
}

