/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.sort;

import java.util.Arrays;

/**
 * @author rolex
 * @since 2020
 */
public class InsertionSortExample {

    public static void main(String[] args) {
        int[] arr = {6, 2, 4, 7, 1};
        insertionSort(arr);
        System.out.println(Arrays.toString(arr));
    }

    /**
     * 1. 将数组分成 2 组，一组有序一组无序
     * 2. 将无序组中的数依次取出来，从有序组中的最后一个数开始，依次比较，确定位置后插入有序组
     * 3. n 个数需要执行 n-1 次
     * 4. 时间复杂度为 O(n^2)
     *
     * @param arr
     */
    public static void insertionSort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int val = arr[i];
            int index = i - 1;
            while (index >= 0 && arr[index] > val) {
                arr[index + 1] = arr[index];
                index--;
            }
            arr[index + 1] = val;
            System.out.println(Arrays.toString(arr));
        }
    }
}
