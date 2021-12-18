/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.stack;

import java.nio.file.StandardWatchEventKinds;

/**
 * @author rolex
 * @since 2020
 */
public class StackExample {

    public static void main(String[] args) {
        Stack stack = new Stack(5);
        System.out.printf("添加元素：%d，栈顶元素为：%d\n", stack.push(1), stack.peek());
        System.out.printf("添加元素：%d，栈顶元素为：%d\n", stack.push(2), stack.peek());
        System.out.printf("添加元素：%d，栈顶元素为：%d\n", stack.push(3), stack.peek());
        System.out.printf("添加元素：%d，栈顶元素为：%d\n", stack.push(4), stack.peek());
        System.out.printf("添加元素：%d，栈顶元素为：%d\n", stack.push(5), stack.peek());


        while (!stack.isEmpty()) {
            System.out.printf("取出元素：%d，栈顶元素为：%d\n", stack.pop(), stack.peek());
        }

    }
}

class Stack {

    int capacity;
    int size;
    Object[] arr;
    int top;

    public Stack(int capacity) {
        this.capacity = capacity;
        size = 0;
        arr = new Object[capacity];
        top = -1;
    }

    public int size() {
        return size;
    }

    public Object push(Object o) {
        if (size == capacity) {
            throw new RuntimeException("栈已满");
        }
        arr[++top] = o;
        size++;
        return o;
    }

    public Object pop() {
        if (size == 0) {
            throw new RuntimeException("栈中没有元素");
        }
        Object obj = arr[top];
        top--;
        size--;
        return obj;
    }

    public Object peek() {
        if (size == 0) {
            return null;
        }
        return arr[top];
    }

    public boolean isEmpty() {
        return size == 0;
    }
}
