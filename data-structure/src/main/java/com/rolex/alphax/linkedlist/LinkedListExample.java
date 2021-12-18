/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.linkedlist;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * @author rolex
 * @since 2020
 */
public class LinkedListExample {
    public static void main(String[] args) {
        LinkedList linkedList = new LinkedList();
        linkedList.add(1);
        linkedList.add(2);
        linkedList.add(3);
        linkedList.add(4);
        linkedList.add(5);

        linkedList.remove(3);
        for (int i = 0; i < linkedList.size; i++) {
            System.out.println(linkedList.get(i));
        }
    }
}

class LinkedList {

    int size;
    LinkedListNode head;
    LinkedListNode tail;

    public LinkedList() {
        this.size = 0;
        this.head = null;
        this.tail = null;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return false;
    }

    public boolean add(Object o) {
        LinkedListNode last = tail;
        if (head == null) {
            LinkedListNode node = new LinkedListNode(o, null, null);
            head = node;
            tail = node;
        } else {
            LinkedListNode node = new LinkedListNode(o, last, null);
            last.next = node;
            tail = node;
        }
        size++;
        return true;
    }

    public boolean remove(Object o) {
        for (int i = 0; i < size; i++) {
            LinkedListNode node = node(i);
            if (node.getVal().equals(o)) {
                LinkedListNode prev = node.prev;
                LinkedListNode next = node.next;
                node.prev.next = node.next;
                node.next.prev = node.prev;
                size--;
                return true;
            }
        }
        return false;
    }

    public Object get(int index) {
        return node(index).getVal();
    }

    public LinkedListNode node(int index) {
        if (size == 0) {
            throw new RuntimeException("链表为空");
        }
        LinkedListNode node = head;
        if (index > 0) {
            for (int i = 0; i < index; i++) {
                node = node.getNext();
            }
        }
        return node;
    }
}

class LinkedListNode {
    Object val;
    LinkedListNode prev;
    LinkedListNode next;

    public LinkedListNode(Object val, LinkedListNode prev, LinkedListNode next) {
        this.val = val;
        this.prev = prev;
        this.next = next;
    }

    public Object getVal() {
        return val;
    }

    public void setVal(Object val) {
        this.val = val;
    }

    public LinkedListNode getPrev() {
        return prev;
    }

    public void setPrev(LinkedListNode prev) {
        this.prev = prev;
    }

    public LinkedListNode getNext() {
        return next;
    }

    public void setNext(LinkedListNode next) {
        this.next = next;
    }

    @Override
    public String toString() {
        return "LinkedListNode{" +
                "val=" + val + "}";
    }
}