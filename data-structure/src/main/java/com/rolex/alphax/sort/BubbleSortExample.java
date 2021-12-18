/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.sort;

import java.util.Arrays;

/**
 * @author rolex
 * @since 2020
 */
public class BubbleSortExample {
    public static void main(String[] args) {
        int[] arr = {6, 2, 4, 7, 1};
        bubbleSort(arr);
        System.out.println(Arrays.toString(arr));
    }

    /**
     * 1. n 个数只需要循环 n-1 次
     * 2. 每次循环中只需要比较为排序部分，即 n-1-i
     * 3. 如果在一次循环中没有比较，则已是有序
     * 4. 时间复杂度为 O(n^2)
     *
     * @param arr
     */
    public static void bubbleSort(int[] arr) {
        boolean flag = false;
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    int tmp = arr[j + 1];
                    arr[j + 1] = arr[j];
                    arr[j] = tmp;
                    flag = true;
                }
                System.out.println("第" + (i + 1) + "轮-第" + (j + 1) + "次：" + Arrays.toString(arr));
            }
            if (!flag) {
                break;
            } else {
                flag = false;
            }
        }
    }

}
