/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * 给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度。
 * 输入: "abcabcbb"
 * 输出: 3
 * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
 * <p>
 * 输入: "bbbbb"
 * 输出: 1
 * 解释: 因为无重复字符的最长子串是 "b"，所以其长度为 1。
 * <p>
 * 输入: "pwwkew"
 * 输出: 3
 * 解释: 因为无重复字符的最长子串是 "wke"，所以其长度为 3。
 * <p>
 * 输入: "nfpdmpi"
 * 输出: 5
 * 解释: 因为无重复字符的最长子串是 "nfpdm"，所以其长度为 5。
 * <p>
 * 输入: "tmmzuxt"
 * 输出: 5
 * 解释: 因为无重复字符的最长子串是 "mzuxt"，所以其长度为 5。
 *
 * @author rolex
 * @since 2020
 */
public class Test3 {

    public int lengthOfLongestSubstring(String s) {
        Map<Character, Integer> map = new HashMap<>();
        int len = 0;
        int start = 0;
        for (int i = 0; i < s.length(); i++) {
            Character c = s.charAt(i);
            if (map.get(c) == null) {
                map.put(c, i);
            } else {
                if (map.get(c) + 1 > start) {
                    start = map.get(c) + 1;
                }
                map.put(c, i);
            }
            if (i + 1 - start > len) {
                len = i + 1 - start;
            }
        }
        return len;
    }

    public static void main(String[] args) {
        int len = new Test3().lengthOfLongestSubstring("abcabcbb");
        System.out.println(len);
    }
}
