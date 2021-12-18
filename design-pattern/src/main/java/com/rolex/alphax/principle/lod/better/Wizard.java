/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.principle.lod.better;

/**
 * @author rolex
 * @since 2020
 */
public class Wizard {
    private void first() {
        System.out.println("first step");
    }

    private void second() {
        System.out.println("second step");
    }

    private void third() {
        System.out.println("third step");
    }

    public void installWizard(){
        first();
        second();
        third();
    }
}
