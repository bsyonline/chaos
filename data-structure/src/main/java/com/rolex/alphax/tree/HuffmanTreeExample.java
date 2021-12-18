/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.tree;

import java.util.*;

/**
 * @author rolex
 * @since 2020
 */
public class HuffmanTreeExample {

    public static void main(String[] args) {
        Node node1 = new Node(7, 'a');
        Node node2 = new Node(5, 'b');
        Node node3 = new Node(2, 'c');
        Node node4 = new Node(4, 'd');
        List<Node> list = new ArrayList<>();
        list.add(node1);
        list.add(node2);
        list.add(node3);
        list.add(node4);
        HuffmanTree huffmanTree = new HuffmanTree();
        Node root = huffmanTree.create(list);
        huffmanTree.preOrder(root);
        System.out.println("--");
        Map<Character, String> huffmanCode = huffmanTree.createHuffmanCode(root);
        System.out.println(huffmanCode);
    }
}

class HuffmanTree {

    public void preOrder(Node root) {
        System.out.println(root);
        if (root.left != null) {
            preOrder(root.left);
        }
        if (root.right != null) {
            preOrder(root.right);
        }
    }

    public Map createHuffmanCode(Node node) {
        HashMap codeMap = new HashMap();
        return createHuffmanCode(node, "", new StringBuffer(), codeMap);
    }

    /**
     * 将左子树的 pathNo 记为 0， 又子树的 pathNo 记为 1
     *
     * @param node
     * @param pathNo
     * @param code
     * @param map
     * @return
     */
    public Map createHuffmanCode(Node node, String pathNo, StringBuffer code, Map<Character, String> map) {
        StringBuffer sb = new StringBuffer(code);
        if (node != null) {
            sb.append(pathNo);
            if (node.value == null) {
                createHuffmanCode(node.left, "0", sb, map);
                createHuffmanCode(node.right, "1", sb, map);
            } else {
                map.put(node.value, sb.toString());
            }
        }
        return map;
    }

    public Node create(List<Node> nodes) {
        while (nodes.size() > 1) {
            Collections.sort(nodes);
            Node node0 = nodes.get(0);
            Node node1 = nodes.get(1);
            Node parentNode = new Node(node0.weight + node1.weight, null);
            parentNode.left = node0;
            parentNode.right = node1;
            nodes.remove(node0);
            nodes.remove(node1);
            nodes.add(parentNode);
        }
        return nodes.get(0);
    }
}

class Node implements Comparable<Node> {
    int weight;
    Character value;
    Node left;
    Node right;

    public Node(int weight, Character value) {
        this.weight = weight;
        this.value = value;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Character getValue() {
        return value;
    }

    public void setValue(Character value) {
        this.value = value;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    @Override
    public int compareTo(Node o) {
        return this.weight - o.weight;
    }

    @Override
    public String toString() {
        return "Node{" +
                "weight=" + weight +
                ", value=" + value +
                '}';
    }
}
