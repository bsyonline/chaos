/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.creational.builder;

/**
 * @author rolex
 * @since 2020
 */
public enum Profession {
    Warrior(10), Mage(3), Paladin(10), Hunter(7);
    int point;

    Profession(int point) {
        this.point = point;
    }
}
