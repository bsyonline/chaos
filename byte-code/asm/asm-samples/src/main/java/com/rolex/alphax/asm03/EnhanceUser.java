/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.asm03;

/**
 * @author rolex
 * @since 2020
 */
public class EnhanceUser {
    public String queryById(String uid) {
        StringBuilder sb = new StringBuilder();
        long start = System.nanoTime();
        System.out.println("xxxx");
        System.out.println("xxxx");
        System.out.println("xxxx");
        System.out.println("xxxx");
        sb.append("方法执行耗时(纳秒)->").append(System.nanoTime() - start);
        System.out.println(sb.toString());
        return uid;
    }
}
