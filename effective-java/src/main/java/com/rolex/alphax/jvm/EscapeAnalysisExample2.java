/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.jvm;


/**
 * -XX:+DoEscapeAnalysis 锁消除
 *
 * @author rolex
 * @since 2020
 */
public class EscapeAnalysisExample2 {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 5_000_000; i++) {
            createObject();
        }
        System.out.println("cost = " + (System.currentTimeMillis() - start) + "ms");
    }

    public static void createObject() {
        StringBuffer sb = new StringBuffer();
        sb.append("A");
        sb.append("A");
        sb.append("A");
    }

    public static void optimizedCreateObject() {

    }
}
