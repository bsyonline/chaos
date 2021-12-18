/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.tree;

/**
 * @author rolex
 * @since 2020
 */
public class BinaryTreeExample {
    public static void main(String[] args) {
        TreeNode t1 = new TreeNode(1);
        TreeNode t2 = new TreeNode(2);
        TreeNode t3 = new TreeNode(3);
        TreeNode t4 = new TreeNode(4);
        TreeNode t5 = new TreeNode(5);
        TreeNode t6 = new TreeNode(6);
        TreeNode t7 = new TreeNode(7);
        t1.setLeft(t2);
        t1.setRight(t3);
        t2.setLeft(t4);
        t2.setRight(t5);
        t3.setLeft(t6);
        t3.setRight(t7);

        System.out.println("前序遍历：");
        t1.preOrder();
        System.out.println("中序遍历：");
        t1.middleOrder();
        System.out.println("后续遍历：");
        t1.postOrder();

        System.out.println("前序查找：");
        TreeNode result = t1.preOrderSearch(7);
        System.out.println(result == null ? "没有k" : result.value);

        System.out.println("中序查找：");
        TreeNode result1 = t1.preOrderSearch(7);
        System.out.println(result1 == null ? "没有k" : result1.value);

        System.out.println("后序查找：");
        TreeNode result2 = t1.postOrderSearch(7);
        System.out.println(result2 == null ? "没有k" : result2.value);
    }


}

class TreeNode {
    Integer value;
    TreeNode left;
    TreeNode right;

    public TreeNode(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public TreeNode getLeft() {
        return left;
    }

    public void setLeft(TreeNode left) {
        this.left = left;
    }

    public TreeNode getRight() {
        return right;
    }

    public void setRight(TreeNode right) {
        this.right = right;
    }

    @Override
    public String toString() {
        return "TreeNode{" +
                "value='" + value + '\'' +
                '}';
    }

    public void preOrder() {
        System.out.println(value);
        if (left != null) {
            left.preOrder();
        }
        if (right != null) {
            right.preOrder();
        }
    }

    public void postOrder() {
        if (left != null) {
            left.postOrder();
        }
        if (right != null) {
            right.postOrder();
        }
        System.out.println(value);
    }

    public void middleOrder() {
        if (left != null) {
            left.middleOrder();
        }
        System.out.println(value);
        if (right != null) {
            right.middleOrder();
        }
    }

    public TreeNode preOrderSearch(int k) {
        System.out.println("preOrderSearch");
        if (k == value) {
            return this;
        }
        TreeNode result = null;
        if (left != null) {
            result = left.preOrderSearch(k);
        }
        if (result != null) {
            return result;
        } else {
            if (right != null) {
                result = right.preOrderSearch(k);
            }
        }
        return result;
    }

    public TreeNode middleOrderSearch(int k) {
        TreeNode result = null;
        if (this.left != null) {
            result = this.left.middleOrderSearch(k);
        }
        if (result != null) {
            return result;
        } else {
            System.out.println("middleOrderSearch");
            if (k == this.value) {
                return this;
            }
            if (result != null) {
                return result;
            } else {
                if (this.right != null) {
                    result = this.right.middleOrderSearch(k);
                }
            }
        }
        return result;
    }

    public TreeNode postOrderSearch(int k) {
        TreeNode result = null;
        if (this.left != null) {
            result = this.left.postOrderSearch(k);
        }
        if (result != null) {
            return result;
        } else {
            if (this.right != null) {
                result = this.right.postOrderSearch(k);
            }
        }
        if (result != null) {
            return result;
        } else {
            System.out.println("postOrderSearch");
            if (k == this.value) {
                return this;
            }
        }
        return result;
    }


}
