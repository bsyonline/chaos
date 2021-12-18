/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.sort;

import java.util.Arrays;

/**
 * @author rolex
 * @since 2020
 */
public class HeapsortExample {
    public static void main(String[] args) {
        int[] arr = {6, 2, 4, 7, 1};
        heapsort(arr);
        System.out.println(Arrays.toString(arr));
    }

    public static void heapsort(int arr[]) {
        int n = arr.length;
        int start = arr.length / 2 - 1;
        // Build heap (rearrange array)
        for (int i = start; i >= 0; i--) {
            heapify(arr, n, i);
        }

        // 交换，交换完最大的数就到了数组最后，可以不用做删除节点操作，用剩下的n-1个元素再做 heapify 即可
        for (int i = n - 1; i >= 0; i--) {
            System.out.println("第" + (i) + "heapify");
            swap(arr, 0, i);
            heapify(arr, i, 0);
        }
    }

    /**
     * @param arr
     * @param n   进行 heapify 的元素个数
     * @param i   heapify 的起始元素下标
     */
    private static void heapify(int arr[], int n, int i) {
        //根据起始元素找到其孩子节点
        int c1 = 2 * i + 1; // c1 = 2*i + 1
        int c2 = 2 * i + 2; // c2 = 2*i + 2
        int max = i; // 当前子树最大数的索引

        // 从左子节点c1开始，比较 parent 和 c1 ，如果 parent 小于 c1 则交换
        // 如果没有子节点，则要防止越界
        if (c1 < n && arr[max] < arr[c1]) {
            max = c1;
        }
        if (c2 < n && arr[max] < arr[c2]) {
            max = c2;
        }
        if (max != i) {// 说明需要交换
            swap(arr, max, i);
            heapify(arr, n, max);
        }
    }

    private static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
}
