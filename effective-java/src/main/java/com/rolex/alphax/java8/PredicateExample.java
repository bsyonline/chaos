package com.rolex.alphax.java8;

import java.util.function.Predicate;

public class PredicateExample {
    public static void main(String[] args) {
        Predicate<Integer> p = x -> x > 5;
        System.out.println(p.test(1));
    }
}
