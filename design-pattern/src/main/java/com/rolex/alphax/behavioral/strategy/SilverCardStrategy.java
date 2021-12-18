/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.behavioral.strategy;

/**
 * @author rolex
 * @since 2020
 */
public class SilverCardStrategy implements RateStrategy {
    @Override
    public double fee(double price) {
        return price * 0.9;
    }
}
