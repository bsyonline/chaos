/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.leetcode;

/**
 * 给定一个非空整数数组，除了某个元素只出现一次以外，其余每个元素均出现两次。找出那个只出现了一次的元素。
 *
 * 说明：
 *
 * 你的算法应该具有线性时间复杂度。 你可以不使用额外空间来实现吗？
 *
 * 示例 1:
 *
 * 输入: [2,2,1]
 * 输出: 1
 * 示例 2:
 *
 * 输入: [4,1,2,1,2]
 * 输出: 4
 *
 * @author rolex
 * @since 2020
 */
public class Test136 {
    public int singleNumber(int[] nums) {
        int result = 0;
        for (int num : nums) {
            result = result ^ num;
        }
        return result;
    }

    public static void main(String[] args) {
        int[] arr = {4, 1, 2, 1, 2};
        System.out.println(new Test136().singleNumber(arr));

        System.out.println(4 ^ 1);

        System.out.println(5 ^ 1);
    }
}
