/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.jvm;


/**
 * @author rolex
 * @since 2020
 */
public class OverloadExample {

    public void sayHello(Human guy) {
        System.out.println("hello, guy");
    }

    public void sayHello(Man guy) {
        System.out.println("hello, gentleman");
    }

    public void sayHello(Woman guy) {
        System.out.println("hello, lady");
    }

    public static void main(String[] args) {
        Human man = new Man(); // 静态类型是Human，动态类型是Man
        Human woman = new Woman();
        OverloadExample oe = new OverloadExample();
        /*
            重载是根据静态类型来判断的
         */
        oe.sayHello(man);
        oe.sayHello(woman);
        oe.sayHello((Man) man);
        oe.sayHello((Woman) woman);
    }

    static class Human {

    }

    static class Man extends Human {

    }

    static class Woman extends Human {

    }
}
