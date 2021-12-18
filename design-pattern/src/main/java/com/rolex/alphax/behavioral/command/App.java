/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.behavioral.command;

/**
 * @author rolex
 * @since 2020
 */
public class App {
    public static void main(String[] args) {
        Cook cook = new Cook();
        NoodleTakeOrderCommand noodleTakeOrderCommand = new NoodleTakeOrderCommand(cook);
        RiceTakeOrderCommand riceTakeOrderCommand = new RiceTakeOrderCommand(cook);
        Waiter waiter = new Waiter();
        waiter.setCommand(noodleTakeOrderCommand);
        waiter.orderUp();
        waiter.setCommand(riceTakeOrderCommand);
        waiter.orderUp();
    }
}
