/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.creational.prototype;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;

/**
 * @author rolex
 * @since 2020
 */
@Slf4j
public class LandRover implements Cloneable, Car {

    private ArrayList<String> features = new ArrayList<String>();

    public LandRover() {
        log.info("LandRover Constructor");
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        LandRover landRover = null;
        landRover = (LandRover) super.clone();
        landRover.features = (ArrayList<String>) this.features.clone();
        return landRover;
    }

    public void setValue(String value) {
        this.features.add(value);
    }

    public ArrayList<String> getValue() {
        return this.features;
    }
}
