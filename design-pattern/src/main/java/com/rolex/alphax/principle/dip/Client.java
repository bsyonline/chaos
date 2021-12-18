/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.principle.dip;

/**
 * @author rolex
 * @since 2020
 */
public class Client {
    public static void main(String[] args) {
        IDriver tom = new Driver();
        tom.drive(new Benz());
    }
}
