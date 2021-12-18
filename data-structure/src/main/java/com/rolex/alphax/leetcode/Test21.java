/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.leetcode;

/**
 * 将两个升序链表合并为一个新的升序链表并返回。新链表是通过拼接给定的两个链表的所有节点组成的。 
 * <p>
 * 示例：
 * <p>
 * 输入：1->2->4, 1->3->4
 * 输出：1->1->2->3->4->4
 *
 * @author rolex
 * @since 2020
 */
public class Test21 {
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode head = new ListNode(0);
        ListNode c = head;
        while (l1 != null || l2 != null) {
            if (l1 != null && l2 != null) {
                if (l1.val < l2.val) {
                    c.next = new ListNode(l1.val);
                    l1 = l1.next;
                    c = c.next;
                } else {
                    c.next = new ListNode(l2.val);
                    l2 = l2.next;
                    c = c.next;
                }
            }
            if (l1 == null && l2 != null) {
                c.next = new ListNode(l2.val);
                l2 = l2.next;
                c = c.next;
            }
            if (l2 == null && l1 != null) {
                c.next = new ListNode(l1.val);
                l1 = l1.next;
                c = c.next;
            }
        }
        return head.next;
    }

    public static void main(String[] args) {
        ListNode l1 = new ListNode(1);
        ListNode l2 = new ListNode(2);
        ListNode l3 = new ListNode(4);
        l1.next = l2;
        l2.next = l3;
        ListNode l4 = new ListNode(1);
        ListNode l5 = new ListNode(3);
        ListNode l6 = new ListNode(4);
        l4.next = l5;
        l5.next = l6;
        ListNode listNode = new Test21().mergeTwoLists(l1, l4);

        while (listNode != null) {
            System.out.println(listNode.val);
            listNode = listNode.next;
        }
    }

    static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }
}

