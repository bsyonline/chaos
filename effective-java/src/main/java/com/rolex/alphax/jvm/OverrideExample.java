/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.jvm;


/**
 * @author rolex
 * @since 2020
 */
public class OverrideExample {

    public static void main(String[] args) {
        Human man = new Man();
        Human woman = new Woman();
        Man man1 = new Man();
        Woman woman1 = new Woman();
        man.sayHello();
        woman.sayHello();
        man1.sayHello();
        woman1.sayHello();
    }

    static class Human {
        public void sayHello() {
            System.out.println("hello, guy");
        }
    }

    static class Man extends Human {
        public void sayHello() {
            System.out.println("hello, gentleman");
        }
    }

    static class Woman extends Human {
        public void sayHello() {
            System.out.println("hello, lady");
        }
    }
}
