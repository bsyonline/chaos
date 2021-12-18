/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.behavioral.iterator;

/**
 * @author rolex
 * @since 2020
 */
public class Park implements Collection, Iterable {

    int modCount;
    Car[] cars = new Car[16];
    int idx;

    public Park() {
        this.idx = 0;
        this.modCount = 0;
    }

    @Override
    public void add(Car car) {
        if (idx >= cars.length) {
            throw new RuntimeException("停车场满了");
        }
        cars[idx++] = car;
    }

    @Override
    public Iterator iterator() {
        return new ParkIterator();
    }

    private class ParkIterator implements Iterator {
        int expectedModCount = modCount;

        @Override
        public boolean hasNext() {
            return idx != 0;
        }

        @Override
        public Car next() {
            checkForComodification();
            return cars[--idx];
        }

        private void checkForComodification() {
            if (modCount != expectedModCount) {
                throw new RuntimeException("集合被修改过");
            }
        }
    }
}
