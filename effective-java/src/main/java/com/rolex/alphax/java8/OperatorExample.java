package com.rolex.alphax.java8;

import java.util.function.BinaryOperator;

public class OperatorExample {
    public static void main(String[] args) {
        BinaryOperator<Integer> o = (x, y) -> x + y;
        System.out.println(o.apply(1,2));
    }
}
