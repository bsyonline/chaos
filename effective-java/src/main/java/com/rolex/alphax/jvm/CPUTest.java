/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.jvm;

import java.util.Random;

/**
 * @author rolex
 * @since 2020
 */
public class CPUTest {
    public static void main(String[] args) {
        new Thread(()->{
            try {
                Thread.sleep(Integer.MAX_VALUE);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(()->{
            while (true) {
                int i = new Random().nextInt();
            }
        }).start();
        System.out.println("program started...");
    }
}
