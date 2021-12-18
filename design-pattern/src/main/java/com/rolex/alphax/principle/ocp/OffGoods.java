/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.principle.ocp;

/**
 * @author rolex
 * @since 2020
 */
public class OffGoods implements IGoods {
    private String name;
    private int price;

    public OffGoods(String name, int price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getPrice() {
        return price > 4000 ? price * 90 / 100 : price * 80 / 100;
    }
}
