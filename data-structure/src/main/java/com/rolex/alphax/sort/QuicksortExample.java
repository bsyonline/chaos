/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.sort;

import sun.security.util.Length;

import java.util.Arrays;

/**
 * @author rolex
 * @since 2020
 */
public class QuicksortExample {
    public static void main(String[] args) {
        int[] arr = {6, 2, 4, 7, 1};
        quicksort(arr, 0, arr.length - 1);
        System.out.println(Arrays.toString(arr));
    }

    /**
     * 1. 找到一个基准点，将数组分成两段，将左边大于基准点的数挪到右边，将右边小于基准点的数挪到左边
     * 2. 使用递归
     * 3. 时间复杂度为 O(n * logn)
     *
     * @param arr
     * @param start
     * @param end
     */
    public static void quicksort(int[] arr, int start, int end) {
        int low = start;
        int high = end;
        int q = arr[low];
        while (low < high) {
            if (arr[low] < q) {
                low++;
                continue;
            }
            if (arr[high] > q) {
                high--;
                continue;
            }
            swap(arr, low, high);
        }
        if (start < low - 1) {
            quicksort(arr, start, low - 1);
        }
        if (high + 1 < end) {
            quicksort(arr, high + 1, end);
        }
    }

    private static void swap(int[] arr, int low, int high) {
        int tmp = arr[high];
        arr[high] = arr[low];
        arr[low] = tmp;
    }
}
