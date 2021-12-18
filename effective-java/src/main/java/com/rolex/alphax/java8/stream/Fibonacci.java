package com.rolex.alphax.java8.stream;

import java.util.HashMap;
import java.util.Map;

public class Fibonacci {

    private static final Map<Integer, Integer> cache = new HashMap<>();

    public Fibonacci() {
        cache.put(0, 0);
        cache.put(1, 1);
    }

    public int fibonacci1(int i) {
        Integer integer = cache.computeIfAbsent(i, n -> fibonacci1(n - 1) + fibonacci1(n - 2));
        return integer;
    }

    public void apply(int x) {
        fibonacci1(x);
        System.out.println(cache.values());
    }

    public static void main(String[] args) {
        new Fibonacci().apply(8);
    }
}
