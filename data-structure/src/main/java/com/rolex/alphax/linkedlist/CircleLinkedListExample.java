/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.linkedlist;

/**
 * @author rolex
 * @since 2020
 */
public class CircleLinkedListExample {

    public static void main(String[] args) {
        CircleLinkedList circleLinkedList = new CircleLinkedList();
        circleLinkedList.add(1);
        circleLinkedList.add(2);
        circleLinkedList.add(3);
        circleLinkedList.add(4);
        circleLinkedList.add(5);
        for (int i = 0; i < circleLinkedList.size(); i++) {
            System.out.println(circleLinkedList.get(i));
        }

        System.out.println("--");
        circleLinkedList.poll(3);
    }
}

class CircleLinkedList {
    int size;
    CircleLinkedListNode head;
    CircleLinkedListNode tail;

    public CircleLinkedList() {
        size = 0;
        head = null;
    }

    public void poll(int step) {
        CircleLinkedListNode c = head;
        int count = 0;
        while (size > 1) {
            while (count < step) {
                if (count == step - 1) {
                    Object obj = remove(c);
                    c = c.next;
                    System.out.println(obj);
                    count = 0;
                    break;
                }
                c = c.next;
                count++;
            }
        }
        System.out.println(c.getVal());
    }

    public int size() {
        return size;
    }

    public void add(Object o) {
        if (size == 0) {
            CircleLinkedListNode current = new CircleLinkedListNode(o, null);
            head = current;
            tail = current;
        } else {
            CircleLinkedListNode current = new CircleLinkedListNode(o, head);
            CircleLinkedListNode lastTail = tail;
            lastTail.next = current;
            tail = current;
        }
        size++;
    }

    public Object get(int index) {
        return node(index).getVal();
    }

    public Object remove(CircleLinkedListNode node) {
        if (node == head) {
            head = node.next;
            tail.next = head;
        } else {
            int currentIndex = 0;
            for (int i = 0; i < size; i++) {
                CircleLinkedListNode c = node(i);
                if (c == node) {
                    currentIndex = i;
                }
            }
            int prevIndex = currentIndex - 1;
            int nextIndex = currentIndex + 1;
            CircleLinkedListNode prevNode = node(prevIndex);
            CircleLinkedListNode nextNode = node(nextIndex);
            prevNode.next = nextNode;
        }
        size--;
        return node.getVal();
    }

    public CircleLinkedListNode node(int index) {
        if (size == 0) {
            throw new RuntimeException("链表为空");
        } else {
            CircleLinkedListNode node = head;
            if (index > 0) {
                for (int i = 0; i < index; i++) {
                    node = node.next;
                }
            }
            return node;
        }
    }
}

class CircleLinkedListNode {
    Object val;
    CircleLinkedListNode next;

    public CircleLinkedListNode(Object val, CircleLinkedListNode next) {
        this.val = val;
        this.next = next;
    }

    public Object getVal() {
        return val;
    }

    public void setVal(Object val) {
        this.val = val;
    }

    public CircleLinkedListNode getNext() {
        return next;
    }

    public void setNext(CircleLinkedListNode next) {
        this.next = next;
    }
}