/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.principle.ocp;

/**
 * @author rolex
 * @since 2020
 */
public class Mobile implements IGoods {
    private String name;
    private int price;

    public Mobile(String name, int price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public int getPrice() {
        return price;
    }

    @Override
    public String getName() {
        return name;
    }
}
