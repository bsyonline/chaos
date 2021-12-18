/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.test;

/**
 * @author rolex
 * @since 2020
 */
public class Test5 {
    public static void main(String[] args) {
        Integer a = 1;
        Integer b = 126;
        Integer c = 127;
        Integer d = 127;
        Integer e = 128;
        Integer f = 128;
        Long g = 127L;
        Integer h = new Integer(1);
        Integer l = new Integer(1);
        System.out.println(c == d);
        System.out.println(e == f); // Integer -128~127 使用cache ，其他都是用 new Integer()
        System.out.println(e.intValue() == f.intValue()); // Integer -128~127 使用cache ，其他都是用 new Integer()
        System.out.println(c == (a + b));
        System.out.println(c.equals(a + b));
        System.out.println(g == (a + b));
        System.out.println(a == h);
        System.out.println(h == l);
    }
}
