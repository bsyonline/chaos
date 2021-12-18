/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.search;

/**
 * @author rolex
 * @since 2020
 */
public class BinarySearchExample {
    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        int i = binarySearch(arr, 0, arr.length, 1);
        System.out.println(i);
    }

    public static int binarySearch(int[] arr, int start, int end, int k) {
        System.out.println("递归1次");
        int middle = start + (end + start) >> 1;
            if (k == arr[middle]) {
                return middle;
            }
            if (k < arr[middle]) {
                return binarySearch(arr, start, middle - 1, k);
            }
            if (k > arr[middle]) {
                return binarySearch(arr, middle + 1, end, k);
            }
        return -1;
    }
}
