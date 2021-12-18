/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.creational.factory.factorymethod;

/**
 * @author rolex
 * @since 2020
 */
public class Factory {
    CarFactory carFactory;

    public Factory(CarFactory carFactory) {
        this.carFactory = carFactory;
    }

    public Car create() {
        return carFactory.create();
    }
}
