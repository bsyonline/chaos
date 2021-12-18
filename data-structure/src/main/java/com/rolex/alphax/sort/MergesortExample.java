/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.sort;

import java.util.Arrays;

/**
 * @author rolex
 * @since 2020
 */
public class MergesortExample {
    public static void main(String[] args) {
        int[] arr = {6, 2, 4, 7, 1};
        mergesort(arr, 0, arr.length - 1);
        System.out.println(Arrays.toString(arr));
    }

    /**
     * 1. n 个数需要合并 n-1 次
     * 2. 时间复杂度为 O(n * logn)
     *
     * @param arr
     * @param left
     * @param right
     */
    public static void mergesort(int[] arr, int left, int right) {
        if (left == right) {
            return;
        }
        int middle = (left + right) / 2;
        mergesort(arr, left, middle);
        mergesort(arr, middle + 1, right);
        merge(arr, left, middle, right);
    }

    private static void merge(int[] arr, int left, int middle, int right) {
        System.out.printf("left=%d,right=%d\n", left, right);
        int[] tmp = new int[right - left + 1];
        int t = 0;
        int i = left;
        int j = middle + 1;
        //从左右两端开始比较，较小的数放到临时数组
        while (i <= middle && j <= right) {
            tmp[t++] = arr[i] < arr[j] ? arr[i++] : arr[j++];
        }
        //如果左边或右边还有剩余，就依次拷贝到临时数组
        while (i <= middle) {
            tmp[t++] = arr[i++];
        }
        while (j <= right) {
            tmp[t++] = arr[j++];
        }
        //将临时数组拷贝回原数组
        for (int k = 0; k < t; k++) {
            arr[left + k] = tmp[k];
        }
    }
}
