/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.jvm;


/**
 * -XX:-DoEscapeAnalysis
 * -XX:+DoEscapeAnalysis 锁粗化
 *
 * @author rolex
 * @since 2020
 */
public class EscapeAnalysisExample4 {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        m1();
        System.out.println("cost = " + (System.currentTimeMillis() - start) + "ms");
    }

    public static void m1() {
        for (int i = 0; i < 5_000_000; i++) {
            synchronized (new Object()) {

            }
        }
    }

    public static void optimizedM1() {
        synchronized (new Object()) {
            for (int i = 0; i < 5_000_000; i++) {

            }
        }
    }

}
