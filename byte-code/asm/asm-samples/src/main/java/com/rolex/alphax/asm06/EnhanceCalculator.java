/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.asm06;

/**
 * @author rolex
 * @since 2020
 */
public class EnhanceCalculator {
    public static void main(String[] args) {

    }

    public double divide(int i, int m) {
        try {
            Object var3 = (double) (i / m);
            AsmSample6.point("com.rolex.alphax.asm06.AsmSample6.divide", var3);
            return (double) var3;
        } catch (Exception var4) {
            AsmSample6.point("com.rolex.alphax.asm06.AsmSample6.divide", var4);
            throw var4;
        }
    }
}


