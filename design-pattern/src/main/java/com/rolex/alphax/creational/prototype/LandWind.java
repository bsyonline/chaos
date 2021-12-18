/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.creational.prototype;

import lombok.extern.slf4j.Slf4j;

/**
 * @author rolex
 * @since 2020
 */
@Slf4j
public class LandWind implements Car {
    LandRover landRover;

    public LandWind(LandRover landRover) {
        log.info("LandWind Constructor");
        this.landRover = landRover;
    }

    Car create() throws CloneNotSupportedException {
        Car clone = (Car)landRover.clone();
        return clone;
    }
}
