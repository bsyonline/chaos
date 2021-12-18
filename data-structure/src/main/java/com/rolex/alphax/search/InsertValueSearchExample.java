/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.search;

/**
 * @author rolex
 * @since 2020
 */
public class InsertValueSearchExample {

    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        int i = insertValueSearch(arr, 0, arr.length, -1);
        System.out.println(i);
    }

    public static int insertValueSearch(int[] arr, int start, int end, int k) {
        if (start > end || k < arr[start] || k > arr[end - 1]) {
            return -1;
        }
        end -= 1;
        int middle = start + (end - start) * (k - arr[start]) / (arr[end] - arr[start]);
        if (k == arr[middle]) {
            return middle;
        }
        if (k < arr[middle]) {
            insertValueSearch(arr, start, middle - 1, k);
        }
        if (k > arr[middle]) {
            insertValueSearch(arr, middle + 1, end, k);
        }
        return -1;
    }
}
