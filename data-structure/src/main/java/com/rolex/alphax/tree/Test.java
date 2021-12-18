/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.tree;

/**
 * @author rolex
 * @since 2020
 */
public class Test {
    public static void main(String[] args) {
        TreeNode1 t1 = new TreeNode1(1);
        TreeNode1 t2 = new TreeNode1(2);
        TreeNode1 t3 = new TreeNode1(3);
        TreeNode1 t4 = new TreeNode1(4);
        TreeNode1 t5 = new TreeNode1(5);
        TreeNode1 t6 = new TreeNode1(6);
        t1.left = t2;
        t1.mid = t3;
        t1.right = t4;
        t2.left = t5;
        t2.right = t6;
        System.out.println(new Test().deep(t1));
    }

    public int deep(TreeNode1 node) {
        if (node.left == null && node.mid == null && node.right == null)
            return 1;
        int max = 0;
        if (node.left != null) {
            max = getMax(max, node.left);
        }
        if (node.mid != null) {
            max = getMax(max, node.mid);
        }
        if (node.right != null) {
            max = getMax(max, node.right);
        }
        return max;
    }

    private int getMax(int max, TreeNode1 mid) {
        int d;
        d = deep(mid) + 1;
        if (max < d) {
            max = d;
        }
        return max;
    }
}

class TreeNode1 {
    int val;
    TreeNode1 left;
    TreeNode1 right;
    TreeNode1 mid;

    TreeNode1(int x) {
        val = x;
    }
}