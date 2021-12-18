/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.stack;

import java.util.Stack;

/**
 * stack 实现 queue
 *
 * @author rolex
 * @since 2020
 */
public class StackQueue {
    private Stack<Integer> stack1;
    private Stack<Integer> stack2;

    public StackQueue(){
        stack1 = new Stack<>();
        stack2 = new Stack<>();
    }

    private void transfer(){
        if(stack2.isEmpty()) {
            while (!stack1.isEmpty()) {
                stack2.push(stack1.pop());
            }
        }
    }

    public void add(Integer e){
        stack1.push(e);
        transfer();
    }

    public Integer poll(){
        if(stack2.isEmpty()){
            return null;
        }
        transfer();
        return stack2.pop();
    }

    public static void main(String[] args) {
        StackQueue queue = new StackQueue();
        queue.add(1);
        queue.add(2);
        queue.add(3);
        System.out.println(queue.poll());
        queue.add(4);
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());
    }
}