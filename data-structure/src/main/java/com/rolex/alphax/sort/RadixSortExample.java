/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.sort;

import java.util.Arrays;

/**
 * @author rolex
 * @since 2020
 */
public class RadixSortExample {
    public static void main(String[] args) {
        int[] arr = {61, 202, 4, 7, 1};
        radixSort(arr, 3);
        System.out.println(Arrays.toString(arr));
    }

    /**
     * 1. 将数按 个位 十位 百位 ... 分布到 10 个桶中
     * 2. 时间复杂度为 O(n * logn)
     * 3. 空间换时间， n 个数需要 (n * 11 * 4) 字节的空间
     *
     * @param arr
     */
    public static void radixSort(int[] arr, int max) {
        int[][] buckets = new int[10][arr.length];
        int[] bucketIndexArr = new int[10];
        for (int m = 0, n = 1; m < max; m++, n *= 10) {
            for (int i = 0; i < arr.length; i++) {
                int bucketNo = arr[i] / n % 10;
                buckets[bucketNo][bucketIndexArr[bucketNo]++] = arr[i];
            }
            int index = 0;
            for (int i = 0; i < bucketIndexArr.length; i++) {
                if (bucketIndexArr[i] > 0) {
                    for (int j = 0; j < bucketIndexArr[i]; j++) {
                        arr[index++] = buckets[i][j];
                    }
                }
            }
            bucketIndexArr = new int[10];
            System.out.println(Arrays.toString(arr));
        }

    }
}
