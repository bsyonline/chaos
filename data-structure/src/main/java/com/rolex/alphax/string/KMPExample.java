/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.string;


import java.util.Arrays;

/**
 * @author rolex
 * @since 2020
 */
public class KMPExample {
    public static void main(String[] args) {
        String s1 = "ABAABCDAABABAABA";
        String pattern = "AABA";
        int[] next = next(pattern);
        int[] prefix = prefixTable(next);
        System.out.println(Arrays.toString(next));
        System.out.println(Arrays.toString(prefix));
        kmpSearch(s1, pattern);
    }

    /**
     * @param s1
     * @param pattern
     */
    public static void kmpSearch(String s1, String pattern) {
        int[] next = next(pattern);
        int[] prefixTable = prefixTable(next);
        int i = 0;
        int j = 0;
        boolean found = false;
        while (i < s1.length()) {
            if (s1.charAt(i) == pattern.charAt(j)) {
                if (j == pattern.length() - 1) { //j 等于 pattern.length 并且 pattern 最后一位置和 s1 的 i 位置匹配，说明整体匹配，即找到
                    System.out.println("found at " + (i - j));
                    found = true;
                    // 如果需要继续找，则从 前缀表的 j 位置继续匹配
                    j = prefixTable[j];
                } else {
                    i++;
                    j++;
                }
            } else {
                // 如果不匹配，则从前缀表的 j 位置开始重新匹配
                j = prefixTable[j];
                // 如果第一个位置都不匹配，则整体后移一位
                if (j == -1) {
                    i++;
                    j++;
                }
            }
        }
        if (!found) {
            System.out.println("not found");
        }
    }

    /**
     * 构造 prefix table， next 数组整体后移 1 位，第一个位置置为 -1
     *
     * @param next
     * @return
     */
    private static int[] prefixTable(int[] next) {
        int[] prefix = new int[next.length];
        prefix[0] = -1;
        for (int i = 0; i < next.length - 1; i++) {
            prefix[i + 1] = next[i];
        }
        return prefix;
    }

    /**
     * 前缀表
     *
     * @param pattern
     * @return
     */
    private static int[] next(String pattern) {
        int[] next = new int[pattern.length()];
        next[0] = 0;
        for (int i = 1, j = 0; i < pattern.length(); i++) {
            while (j > 0 && pattern.charAt(i) != pattern.charAt(j)) {
                j = next[j - 1];
            }
            // 如果
            if (pattern.charAt(i) == pattern.charAt(j)) {
                j++;
            }
            next[i] = j;
        }
        return next;
    }
}
