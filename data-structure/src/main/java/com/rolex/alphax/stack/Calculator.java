/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.stack;

import java.util.Stack;

/**
 * @author rolex
 * @since 2020
 */
public class Calculator {
    public static void main(String[] args) {
        String s = "3+2*6-1";
        int result = calculate(s);
        System.out.println(result);
    }

    public static int calculate(String s) {
        if (s == null && s.equals("")) {
            throw new RuntimeException("参数错误");
        }
        Stack<Integer> stack1 = new Stack<Integer>();//放数的stack
        Stack stack2 = new Stack();//放运算符的stack
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                stack1.push(Character.digit(c, 10));
            } else {
                if (stack2.isEmpty()) {// 如果运算符stack为空，直接入栈
                    stack2.push(c);
                } else {
                    //如果运算符stack不为空，需要判断运算符优先级
                    char e = c;//准备入站的运算符
                    char op = (Character) stack2.peek();

                    if (lowerThan(e, op)) {
                        //比栈顶运算符优先级低，取出栈顶运算符进行运算
                        int a = stack1.pop();
                        int b = stack1.pop();
                        op = (Character) stack2.pop();
                        int result = cal(b, a, op);
                        stack1.push(result);
                        stack2.push(e);
                    } else {
                        stack2.push(e);
                    }
                }
            }
        }
        //如果运算符栈中还有元素，继续去出来进行运算
        while (!stack2.isEmpty()) {
            char op = (Character) stack2.pop();
            int a = stack1.pop();
            int b = stack1.pop();
            stack1.push(cal(b, a, op));
        }
        return stack1.pop();
    }

    public static boolean lowerThan(char op1, char op2) {
        int score1 = score(op1);
        int score2 = score(op2);
        return score1 < score2;
    }

    private static int score(char op) {
        switch (op) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
        }
        return 0;
    }

    private static int cal(int a, int b, char op) {
        int result = 0;
        switch (op) {
            case '+':
                result = a + b;
                break;
            case '-':
                result = a - b;
                break;
            case '*':
                result = a * b;
                break;
            case '/':
                result = a / b;
                break;
        }
        return result;
    }
}
