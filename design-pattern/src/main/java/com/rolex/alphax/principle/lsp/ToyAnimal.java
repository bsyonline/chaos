/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.principle.lsp;

/**
 * @author rolex
 * @since 2020
 */
public class ToyAnimal {
    Animal animal;
    String fur;

    ToyAnimal(Animal animal) {
        this.animal = animal;
    }

    public String getFur() {
        return animal.fur;
    }

    void silence() {
        System.out.println("toy do not shout");
    }
}
