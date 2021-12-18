/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.behavioral.state;

/**
 * @author rolex
 * @since 2020
 */
public class App {
    public static void main(String[] args) {
        Mario mario = new Mario();
        mario.getMushRoom();
        System.out.println("mario state: " + mario.getState());
        mario.meetMonster();
        System.out.println("mario state: " + mario.getState());
    }
}
