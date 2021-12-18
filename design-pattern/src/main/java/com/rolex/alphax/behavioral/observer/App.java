/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.behavioral.observer;

/**
 * @author rolex
 * @since 2020
 */
public class App {
    public static void main(String[] args) {
        Weather weather = new Weather();
        weather.addObserver(new CMA());
        weather.addObserver(new NWS());

        weather.temperatureChange();
        weather.humidityChanges();
    }
}
