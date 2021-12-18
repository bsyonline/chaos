/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.jvm;

/**
 * -XX:+PrintGC -Xms5M -Xmn5M -XX:-DoEscapeAnalysis 堆上分配
 * -XX:+PrintGC -Xms5M -Xmn5M -XX:+DoEscapeAnalysis 栈上分配
 *
 *
 * @author rolex
 * @since 2020
 */
public class EscapeAnalysisExample1 {
    public static void main(String[] args) {
        for (int i = 0; i < 5_000_000; i++) {
            createObject();
        }
    }

    public static void createObject() {
        new Object();
    }
}
