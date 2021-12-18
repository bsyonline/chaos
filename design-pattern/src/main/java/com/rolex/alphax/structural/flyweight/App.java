/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.structural.flyweight;

/**
 * 背包的药水是重复的，只是坐标不同，使用同一个对象，可以节省内存
 *
 * @author rolex
 * @since 2020
 */
public class App {
    public static void main(String[] args) {
        Bag bag = new Bag();
        bag.pushPotion();
        bag.show();
    }
}
