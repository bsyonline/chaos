/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.creational.factory.factorymethod;

/**
 * @author rolex
 * @since 2020
 */
public class BenzFactory implements CarFactory {
    @Override
    public Car create() {
        return new Benz();
    }
}
