/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.jvm;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @author rolex
 * @since 2020
 */
public class OverloadExample2 {
    public int[] twoSum(int[] nums, int target) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (map.get(target-nums[i]) != null)
                return new int[]{i, map.get(target-nums[i])};
            map.put(nums[i], i);
        }
        return new int[]{};
    }

    public static void main(String[] args) {
        int[] arr = new int[]{3, 2, 4};
        OverloadExample2 o = new OverloadExample2();
        int[] ints = o.twoSum(arr, 6);
        System.out.println(Arrays.toString(ints));
    }
}