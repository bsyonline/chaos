/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.recursion;

/**
 * @author rolex
 * @since 2020
 */
public class MazeExample {

    public static void main(String[] args) {
        int[][] arr = new int[8][8];
        for (int i = 0; i < arr.length; i++) {
            arr[i][0] = 1;
            arr[i][7] = 1;
        }

        for (int i = 0; i < arr[0].length; i++) {
            arr[0][i] = 1;
            arr[7][i] = 1;
        }

        arr[3][1] = 1;
        arr[3][2] = 1;
        arr[3][3] = 1;
        arr[2][3] = 1;
        arr[4][5] = 1;
        arr[5][5] = 1;
        arr[6][5] = 1;

        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                System.out.printf("%d  ", arr[i][j]);
            }
            System.out.println();
        }

        start(arr, 1, 1);

        System.out.println("迷宫结果：");
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                System.out.printf("%d  ", arr[i][j]);
            }
            System.out.println();
        }
    }

    public static boolean start(int[][] arr, int i, int j) {
        if (arr[6][6] == 2) { // 走到右下角结束
            return true;
        } else {
            if (arr[i][j] == 0) {// 如果是0，表示还有没有走过
                arr[i][j] = 2;
                if (start(arr, i + 1, j)) {
                    return true;
                } else if (start(arr, i, j + 1)) {
                    return true;
                } else if (start(arr, i - 1, j)) {
                    return true;
                } else if (start(arr, i, j - 1)) {
                    return true;
                } else {
                    arr[i][j] = 3;
                    return false;
                }
            } else {
                return false;
            }
        }
    }

}
