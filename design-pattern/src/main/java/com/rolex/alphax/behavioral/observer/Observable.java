/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.behavioral.observer;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author rolex
 * @since 2020
 */
public class Observable<O extends Observer> {

    private List<O> observers = new CopyOnWriteArrayList();

    public void addObserver(O observer) {
        observers.add(observer);
    }

    public void removeObserver(O observer) {
        observers.remove(observer);
    }

    public void notifyObservers() {
        observers.forEach(o -> o.update());
    }
}
