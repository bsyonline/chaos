/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.principle.dip;

/**
 * @author rolex
 * @since 2020
 */
public class Driver implements IDriver {
    @Override
    public void drive(ICar car) {
        System.out.println("driving car");
        car.run();
    }
}
