/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.string;

/**
 * @author rolex
 * @since 2020
 */
public class BruteForceExample {
    public static void main(String[] args) {
        String s1 = "ABAABABCDEAB";
        String s2 = "ABC";
        int i = bruteForce(s1, s2);
        System.out.println("index=" + i);
    }

    public static int bruteForce(String s1, String s2) {
        char[] arr1 = s1.toCharArray();
        char[] arr2 = s2.toCharArray();
        int i = 0;
        int j = 0;
        while (i < arr1.length && j < arr2.length) {
            if (arr1[i] == arr2[j]) {
                i++;
                j++;
            } else {
                i = i - j + 1;
                j = 0;
            }
        }
        if (j == arr2.length) {
            return i - j;
        } else {
            return -1;
        }
    }
}
