/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.sort;

import java.util.Arrays;

/**
 * @author rolex
 * @since 2020
 */
public class SelectionSortExample {

    public static void main(String[] args) {
        int[] arr = {6, 2, 4, 7, 1};
        selectionSort(arr);
        System.out.println(Arrays.toString(arr));
    }

    /**
     * 1. n 个数需要循环 n-1 次
     * 2. 默认选择第一个数，假定其最小，从下一个数开始依次向后比较，如果有更小的数，记录下值和索引
     * 3. 如果第一个数和选定的数不是同一个数，将第一个位置的数与选定的数交换
     * 4. 时间复杂度为 O(n^2)
     *
     * @param arr
     */
    public static void selectionSort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            int minIndex = i;
            int min = arr[minIndex];
            for (int j = i + 1; j < arr.length; j++) {
                if (min > arr[j]) {
                    minIndex = j;// 将更小的数记录下来，作为选定的最小数
                    min = arr[j];
                }
            }
            if (minIndex != i) {
                arr[minIndex] = arr[i];
                arr[i] = min;
            }
        }
    }
}
