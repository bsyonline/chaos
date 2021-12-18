/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.leetcode;

/**
 * 给定一个链表，判断链表中是否有环。
 *
 * 为了表示给定链表中的环，我们使用整数 pos 来表示链表尾连接到链表中的位置（索引从 0 开始）。 如果 pos 是 -1，则在该链表中没有环。
 *
 * 示例 1：
 *
 * 输入：head = [3,2,0,-4], pos = 1
 * 输出：true
 * 解释：链表中有一个环，其尾部连接到第二个节点。
 *
 * 示例 2：
 *
 * 输入：head = [1,2], pos = 0
 * 输出：true
 * 解释：链表中有一个环，其尾部连接到第一个节点。
 *
 * 示例 3：
 *
 * 输入：head = [1], pos = -1
 * 输出：false
 * 解释：链表中没有环。
 *
 * @author rolex
 * @since 2020
 */
public class Test141 {
    static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }
    }

    public boolean hasCycle(ListNode head) {
        if (head == null || head.next == null) return false;
        ListNode l1 = head.next;
        ListNode l2 = head.next.next;
        while (l1 != l2) {
            if (l2 == null || l2.next == null) {
                return false;
            }
            l1 = l1.next;
            l2 = l2.next.next;
        }
        return true;
    }

    public static void main(String[] args) {
        ListNode l1 = new ListNode(1);
        ListNode l2 = new ListNode(2);
//        ListNode l3 = new ListNode(0);
//        ListNode l4 = new ListNode(-4);
//        l1.next = l2;
//        l2.next = l3;
//        l3.next = l4;
//        l4.next = l2;
        l1.next = l2;
//        l2.next = l1;
        System.out.println(new Test141().hasCycle(l1));
    }
}
