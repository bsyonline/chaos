/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.tree;

/**
 * @author rolex
 * @since 2020
 */
public class BinarySortTreeExample {
    public static void main(String[] args) {
        int[] arr = {7, 3, 10, 12, 5, 1, 9, 2};
        BinarySortTree binarySortTree = new BinarySortTree();
        for (int i = 0; i < arr.length; i++) {
            binarySortTree.add(new BinarySortTreeNode(arr[i]));
        }
        binarySortTree.middleOrder();
        BinarySortTreeNode root = binarySortTree.getRoot();
        System.out.println("root = " + root);
        BinarySortTreeNode result = binarySortTree.search(new BinarySortTreeNode(19));
        System.out.println("search result = " + result);
        BinarySortTreeNode minLeft = binarySortTree.minLeft(binarySortTree.getRoot());
        System.out.println("minleft = " + minLeft);
        binarySortTree.delete(new BinarySortTreeNode(1));
        binarySortTree.delete(new BinarySortTreeNode(10));
        binarySortTree.delete(new BinarySortTreeNode(3));
        binarySortTree.delete(new BinarySortTreeNode(12));
        binarySortTree.delete(new BinarySortTreeNode(2));

        System.out.println("--");
        binarySortTree.middleOrder();
    }
}

class BinarySortTree {
    BinarySortTreeNode root;

    public BinarySortTreeNode getRoot() {
        return root;
    }

    public void setRoot(BinarySortTreeNode root) {
        this.root = root;
    }

    public void add(BinarySortTreeNode node) {
        if (root == null) {
            root = node;
        } else {
            root.add(node);
        }
    }

    public void middleOrder() {
        root.middleOrder();
    }

    public BinarySortTreeNode minLeft(BinarySortTreeNode root) {
        return root.minLeft();
    }

    public void delete(BinarySortTreeNode node) {
        BinarySortTreeNode t = search(node);
        if (t == null) {
            System.out.println("节点不存在");
            return;
        }
        if (t.parent != null) {
            if (node.value < t.parent.value) {
                t.parent.deleteLeft();
            } else {
                t.parent.deleteRight();
            }
        } else {

        }
    }

    public BinarySortTreeNode search(BinarySortTreeNode t) {
        return search(root, t);
    }

    public BinarySortTreeNode search(BinarySortTreeNode node, BinarySortTreeNode t) {
        if (node.value == t.value) {
            return node;
        } else {
            if (node.value > t.value) {
                if (node.left != null) {
                    return search(node.left, t);
                } else {
                    return null;
                }
            } else {
                if (node.right != null) {
                    return search(node.right, t);
                } else {
                    return null;
                }
            }
        }
    }
}

class BinarySortTreeNode {
    Integer value;
    BinarySortTreeNode left;
    BinarySortTreeNode right;
    BinarySortTreeNode parent;

    public BinarySortTreeNode(Integer value) {
        this.value = value;
    }

    public void deleteLeft() {
        if (this.left != null) {
            BinarySortTreeNode delNode = this.left;
            if (delNode.left == null && delNode.right != null) {
                delNode.parent.left = delNode.right;
                delNode.right.parent = delNode.parent;
            }
            if (delNode.right == null && delNode.left != null) {
                delNode.parent.left = delNode.left;
                delNode.left.parent = delNode.parent;
            }
            if (delNode.right != null && delNode.left != null) {
                BinarySortTreeNode c2 = delNode.right;
                BinarySortTreeNode minLeft = c2.minLeft();
                minLeft.left = delNode.left;
                delNode.left.parent = minLeft;
                delNode.parent.left = c2;
                c2.parent = delNode.parent;
            }
            if (delNode.right == null && delNode.left == null) {
                delNode.parent.left = null;
            }
        }
    }

    public void deleteRight() {
        if (this.right != null) {
            BinarySortTreeNode delNode = this.right;
            if (delNode.left == null && delNode.right != null) {
                delNode.parent.right = delNode.right;
                delNode.right.parent = delNode.parent;
            }
            if (delNode.right == null && delNode.left != null) {
                delNode.parent.right = delNode.left;
                delNode.left.parent = delNode.parent;
            }
            if (delNode.right != null && delNode.left != null) {
                BinarySortTreeNode c1 = delNode.left;
                BinarySortTreeNode maxRight = c1.maxRight();
                maxRight.right = delNode.right;
                delNode.right.parent = maxRight;
                delNode.parent.right = c1;
                c1.parent = delNode.parent;
            }
            if (delNode.right == null && delNode.left == null) {
                delNode.parent.right = null;
            }
        }
    }

    public BinarySortTreeNode maxRight() {
        if (this.right == null) {
            return this;
        } else {
            return this.right.maxRight();
        }
    }

    public BinarySortTreeNode minLeft() {
        if (this.left == null) {
            return this;
        } else {
            return this.left.minLeft();
        }
    }

    public void add(BinarySortTreeNode node) {
        if (node.value <= this.value) {
            if (this.left == null) {
                this.left = node;
                node.parent = this;
            } else {
                this.left.add(node);
            }
        } else {
            if (this.right == null) {
                this.right = node;
                node.parent = this;
            } else {
                this.right.add(node);
            }
        }
    }

    public void middleOrder() {
        if (this.left != null) {
            this.left.middleOrder();
        }
        System.out.println(this);
        if (this.right != null) {
            this.right.middleOrder();
        }
    }

    public BinarySortTreeNode getParent() {
        return parent;
    }

    public void setParent(BinarySortTreeNode parent) {
        this.parent = parent;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public BinarySortTreeNode getLeft() {
        return left;
    }

    public void setLeft(BinarySortTreeNode left) {
        this.left = left;
    }

    public BinarySortTreeNode getRight() {
        return right;
    }

    public void setRight(BinarySortTreeNode right) {
        this.right = right;
    }

    @Override
    public String toString() {
        return "BinarySortTreeNode{" +
                "value=" + value +
                ", parent=" + (parent == null ? null : parent.value) +
                '}';
    }
}