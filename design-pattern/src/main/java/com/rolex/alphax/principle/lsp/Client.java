/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.principle.lsp;

/**
 * @author rolex
 * @since 2020
 */
public class Client {
    public static void main(String[] args) {
        Person alice = new Person();
        Dog dog = new Dog();
        alice.set(dog);
        alice.play();
    }
}
