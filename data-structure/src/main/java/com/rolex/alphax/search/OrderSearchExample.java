/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.search;

/**
 * @author rolex
 * @since 2020
 */
public class OrderSearchExample {
    public static void main(String[] args) {
        int[] arr = {6, 2, 4, 7, 1};
        int i = orderSearch(arr, 4);
        System.out.println(i);
    }

    public static int orderSearch(int[] arr, int k) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == k) {
                return i;
            }
        }
        return -1;
    }
}
