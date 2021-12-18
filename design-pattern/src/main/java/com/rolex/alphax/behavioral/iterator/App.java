/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.behavioral.iterator;

/**
 * @author rolex
 * @since 2020
 */
public class App {
    public static void main(String[] args) {
        Park park = new Park();
        park.add(new Car("京N1122AX", "Benz C200"));
        park.add(new Car("陕Q2AWS8F", "BMW 740"));
        park.add(new Car("粤AN56233", "TOYOTA"));
        park.add(new Car("新BAW14TX", "Benz S600"));
        park.add(new Car("京BMM2EO1", "AUDI A5"));
        Iterator iterator = park.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }
}
