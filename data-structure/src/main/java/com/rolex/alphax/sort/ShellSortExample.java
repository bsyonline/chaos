/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.sort;

import java.util.Arrays;

/**
 * @author rolex
 * @since 2020
 */
public class ShellSortExample {
    public static void main(String[] args) {
        int[] arr = {8, 9, 1, 7, 2, 3, 5, 4, 6, 0};
        shellSort(arr);
        System.out.println(Arrays.toString(arr));
    }

    /**
     * 1. 将数分成 n 组，每组分别进行比较，将结果在分成 n/2 组，每组分别进行比较，直到分组为 0
     * 2. 时间复杂度为 O(n*logn)
     *
     * @param arr
     */
    public static void shellSort(int[] arr) {
        for (int gap = arr.length / 2; gap > 0; gap = gap / 2) {
            selectionSort(arr, gap);
            System.out.println(Arrays.toString(arr));
        }
    }

    private static void selectionSort(int[] arr, int gap) {
        for (int i = gap; i < arr.length; i++) {
            int val = arr[i];
            int index = i - gap;
            while (index >= 0 && arr[index] > val) {
                arr[index + gap] = arr[index];
                index -= gap;
            }
            arr[index + gap] = val;
        }
    }
}
