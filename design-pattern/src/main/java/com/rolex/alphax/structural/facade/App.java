/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.structural.facade;

/**
 * @author rolex
 * @since 2020
 */
public class App {
    public static void main(String[] args) {
        GovernmentFacade governmentFacade = new GovernmentFacade();
        governmentFacade.approve();
    }
}
