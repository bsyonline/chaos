/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.creational.factory.simplefactory;

/**
 * @author rolex
 * @since 2020
 */
public class CarFactory {
    public static Car create(String type) {
        if ("benz".equals(type)) {
            return new Benz();
        } else if ("bmw".equals(type)) {
            return new Bmw();
        } else {
            return null;
        }
    }
}
