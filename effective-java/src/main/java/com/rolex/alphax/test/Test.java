/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.test;

/**
 * 引用传递和值传递问题。
 * 作为参数，基本类型是值传递，对象和数组是引用传递
 *
 * @author rolex
 * @since 2020
 */
public class Test {
    String str = new String("hello");
    int i = 1;
    char[] ch = {'a', 'b'};
    int[] arr = {1, 2};
    User user = new User("zhangsan");

    public static void main(String[] args) {
        Test test = new Test();
        test.change(test.str, test.i, test.ch, test.user);
        System.out.print(test.str + " and ");
        System.out.print(test.i + " and ");
        System.out.print(test.ch);
        System.out.print(" and ");
        System.out.print(test.user.name);
    }

    public void change(String str, int i, char[] ch, User user) {
        str = "test ok";
        i = 2;
        ch[0] = 'c';
        arr[0] = 3;
        user.name = "lisi";
    }

    class User {
        String name;

        public User(String name) {
            this.name = name;
        }
    }
}
