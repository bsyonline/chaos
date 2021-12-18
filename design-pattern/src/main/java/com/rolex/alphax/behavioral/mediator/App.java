/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.behavioral.mediator;

/**
 * @author rolex
 * @since 2020
 */
public class App {
    public static void main(String[] args) {
        ConcreteMediator concreteMediator = new ConcreteMediator();
        CustomerA componentA = new CustomerA(concreteMediator);
        CustomerB componentB = new CustomerB(concreteMediator);
        CustomerC componentC = new CustomerC(concreteMediator);
        CustomerD componentD = new CustomerD(concreteMediator);
        concreteMediator.setCustomerA(componentA);
        concreteMediator.setCustomerB(componentB);
        concreteMediator.setCustomerC(componentC);
        concreteMediator.setCustomerD(componentD);
        componentA.send("四合院一套5000w");
    }
}
