/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.behavioral.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author rolex
 * @since 2020
 */
public class Weather {
    List<WeatherObserver> observers = new ArrayList<>();

    public void addObserver(WeatherObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(WeatherObserver observer) {
        observers.remove(observer);
    }

    public void temperatureChange() {
        observers.forEach(o -> o.update("温度变化"));
    }

    public void humidityChanges() {
        observers.forEach(o -> o.update("湿度变化"));
    }
}
