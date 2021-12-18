/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.test;

/**
 * 子类在进行实例化的时候会调用父类的构造函数
 *
 * @author rolex
 * @since 2020
 */
public class Test3 {
    public static void main(String[] args) {
        Employee e = new Employee("123");
        System.out.println(e.empId);
    }

    static class Person {
        String name = "no name";

        public Person(String nm) {
            name = nm;
        }
    }

    static class Employee extends Person {
        String empId = "0000";

        public Employee(String id) {
            super(id); // 如果没有这句会编译错误
            empId = id;
        }
    }
}


