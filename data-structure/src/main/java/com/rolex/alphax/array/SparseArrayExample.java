/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.array;

/**
 * @author rolex
 * @since 2020
 */
public class SparseArrayExample {

    public static int[][] init() {
        int[][] arr = new int[10][10];
        arr[2][2] = 1;
        arr[3][3] = 2;
        return arr;
    }

    public static int[][] toSparseArr(int[][] arr, int count) {
        int[][] sparseArr = new int[count + 1][3];
        sparseArr[0][0] = arr[0].length;
        sparseArr[0][1] = arr.length;
        sparseArr[0][2] = count;
        count = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                if (arr[i][j] != 0) {
                    ++count;
                    sparseArr[count][0] = i;
                    sparseArr[count][1] = j;
                    sparseArr[count][2] = arr[i][j];
                }
            }
        }

        return sparseArr;
    }

    public static int[][] fromSparseArr(int[][] sparseArr) {
        int[][] arr = new int[sparseArr[0][0]][sparseArr[0][1]];
        for (int i = 1; i < sparseArr.length; i++) {
            arr[sparseArr[i][0]][sparseArr[i][1]] = sparseArr[i][2];
        }
        return arr;
    }

    public static void main(String[] args) {
        /*
            将 10 x 10 的二维数组转成稀疏数组
         */
        int[][] arr = init();
        int count = 0; // 有值的数量
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                if (arr[i][j] != 0) {
                    count++;
                }
                System.out.printf("%d  ", arr[i][j]);
            }
            System.out.println();
        }
        System.out.println(count);

        int[][] sparseArr = toSparseArr(arr, count);
        for (int i = 0; i < sparseArr.length; i++) {
            for (int j = 0; j < sparseArr[i].length; j++) {
                System.out.printf("%d  ", sparseArr[i][j]);
            }
            System.out.println();
        }

        int[][] arr1 = fromSparseArr(sparseArr);
        for (int i = 0; i < arr1.length; i++) {
            for (int j = 0; j < arr1[i].length; j++) {
                System.out.printf("%d  ", arr1[i][j]);
            }
            System.out.println();
        }
    }
}
