/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.tree;

import java.util.LinkedList;

/**
 * @author rolex
 * @since 2020
 */
public class BinaryTreeExample1 {
    public static void main(String[] args) {
        TreeNode2 t1 = new TreeNode2("A");
        TreeNode2 t2 = new TreeNode2("B");
        TreeNode2 t3 = new TreeNode2("C");
        TreeNode2 t4 = new TreeNode2("D");
        TreeNode2 t5 = new TreeNode2("E");
        TreeNode2 t6 = new TreeNode2("F");
        TreeNode2 t7 = new TreeNode2("G");
        t1.setLeft(t2);
        t1.setRight(t3);
        t2.setLeft(t4);
        t2.setRight(t5);
        t3.setLeft(t6);
        t3.setRight(t7);

        new BinaryTreeExample1().test(t1);
    }

    public void test(TreeNode2 root) {
        if (root == null) {
            return;
        }
        LinkedList<TreeNode2> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            TreeNode2 node = queue.poll();
            if (node.left != null) {
                queue.offer(node.left);
            }
            if (node.right != null) {
                queue.offer(node.right);
            }
            System.out.println(node.val);
        }
    }

}

class TreeNode2 {
    String val;
    TreeNode2 left;
    TreeNode2 right;

    public TreeNode2(String val) {
        this.val = val;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public TreeNode2 getLeft() {
        return left;
    }

    public void setLeft(TreeNode2 left) {
        this.left = left;
    }

    public TreeNode2 getRight() {
        return right;
    }

    public void setRight(TreeNode2 right) {
        this.right = right;
    }
}
