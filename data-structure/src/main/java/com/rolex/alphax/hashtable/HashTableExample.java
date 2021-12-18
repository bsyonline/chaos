/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.hashtable;

/**
 * @author rolex
 * @since 2020
 */
public class HashTableExample {

    public static void main(String[] args) {
        HashTable hashTable = new HashTable(10);
        hashTable.add(new LinkedListNode(1, "tom"));
        hashTable.add(new LinkedListNode(2, "alice"));
        hashTable.add(new LinkedListNode(11, "jack"));
        hashTable.add(new LinkedListNode(21, "blues"));
        hashTable.add(new LinkedListNode(4, "bob"));
        hashTable.add(new LinkedListNode(5, "smith"));
        hashTable.add(new LinkedListNode(6, "grace"));
        hashTable.list();
        System.out.println(hashTable.get(11));
    }
}

class HashTable {
    LinkedList[] arr;
    int capacity;

    public HashTable(int capacity) {
        this.capacity = capacity;
        arr = new LinkedList[capacity];
        for (int i = 0; i < capacity; i++) {
            arr[i] = new LinkedList();
        }
    }

    public void add(LinkedListNode o) {
        int no = o.hashCode() % capacity;
        arr[no].add(o);
    }

    public void list() {
        for (int i = 0; i < capacity; i++) {
            arr[i].list();
        }
    }

    public Object get(int id) {
        int no = id % capacity;
        return arr[no].get(id);
    }

}

class LinkedList {
    LinkedListNode head;

    public void add(LinkedListNode o) {
        if (head == null) {
            head = o;
            return;
        } else {
            LinkedListNode c = head;
            while (true) {
                if (c.next == null) {
                    break;
                }
                c = c.next;
            }
            c.next = o;
        }
    }

    public void list() {
        String s = "";
        if (head == null) {
        } else {
            LinkedListNode c = head;
            s += c.value;
            while (true) {
                if (c.next == null) {
                    System.out.println(s);
                    return;
                }
                c = c.next;
                s += "->" + c.value;
            }
        }
    }

    Object get(int id) {
        Object o = null;
        if (head == null) {
            return null;
        } else {
            LinkedListNode c = head;
            while (true) {
                if (c.id == id) {
                    return c;
                }
                c =c.next;
            }
        }
    }
}

class LinkedListNode {
    int id;
    Object value;
    LinkedListNode next;

    public LinkedListNode(int id, Object value) {
        this.id = id;
        this.value = value;
    }

    @Override
    public String toString() {
        return "LinkedListNode{" +
                "id=" + id +
                ", value=" + value +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LinkedListNode that = (LinkedListNode) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}

