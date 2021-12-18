/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.structural.flyweight;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author rolex
 * @since 2020
 */
public class Bag {
    Grid[][] bag = new Grid[4][4];
    static Map<Integer, Potion> potions = new HashMap();

    public void show() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.println(bag[i][j].toString());
            }
        }
    }

    public void pushPotion() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int key = new Random().nextInt(100) % 2 == 0 ? 0 : 1;
                Potion potion = potions.get(key);
                if (potion == null) {
                    if (key == 0) {
                        potion = new HealthPotion();
                    } else {
                        potion = new ManaPotion();
                    }
                    potions.put(key, potion);
                }
                bag[i][j] = new Grid(i, j, potion);
            }
        }
    }
}
