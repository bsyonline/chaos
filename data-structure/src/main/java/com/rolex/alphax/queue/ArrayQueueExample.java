/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.queue;

import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;

/**
 * @author rolex
 * @since 2020
 */
public class ArrayQueueExample {

    public static void main(String[] args) {
        ArrayQueue arrayQueue = new ArrayQueue(5);
        arrayQueue.add(1);
        System.out.printf("添加元素%d,队列大小为%d\n", 1, arrayQueue.size());
        arrayQueue.add(2);
        System.out.printf("添加元素%d,队列大小为%d\n", 2, arrayQueue.size());
        arrayQueue.add(3);
        System.out.printf("添加元素%d,队列大小为%d\n", 3, arrayQueue.size());
        arrayQueue.add(4);
        System.out.printf("添加元素%d,队列大小为%d\n", 4, arrayQueue.size());
        arrayQueue.add(5);
        System.out.printf("添加元素%d,队列大小为%d\n", 5, arrayQueue.size());
//        arrayQueue.add(6);
//        System.out.print("队列元素为：");
//        for(int i=0;i<arrayQueue.size();i++){
//            System.out.printf("%d ", arrayQueue.poll());
//        }
        System.out.println();
//        System.out.printf("删除队列元素：%d,队首元素为：%d\n" , arrayQueue.remove(), arrayQueue.peek());
//        System.out.printf("删除队列元素：%d,队首元素为：%d\n" , arrayQueue.remove(), arrayQueue.peek());
//        System.out.printf("删除队列元素：%d,队首元素为：%d\n" , arrayQueue.remove(), arrayQueue.peek());
//        System.out.printf("删除队列元素：%d,队首元素为：%d\n" , arrayQueue.remove(), arrayQueue.peek());
//        System.out.printf("删除队列元素：%d,队首元素为：%d\n" , arrayQueue.remove(), arrayQueue.peek());

        arrayQueue.poll();
//        arrayQueue.add(6);
//        System.out.printf("添加元素%d,队列大小为%d\n", 6, arrayQueue.size());
    }
}

class ArrayQueue implements Queue {
    int capacity;// 容量
    int head;// 队列头位置
    int tail;// 队列尾位置
    Object[] arr;// 存储队列元素的数组

    public ArrayQueue(int size) {
        this.capacity = size;
        this.head = 0;
        this.tail = 0;
        arr = new Object[capacity];
    }

    public int size() {
        return tail - head;
    }

    public boolean isEmpty() {
        return tail == head;
    }

    public boolean contains(Object o) {
        return false;
    }

    public Iterator iterator() {
        return null;
    }

    public Object[] toArray() {
        return new Object[0];
    }

    public Object[] toArray(Object[] a) {
        return new Object[0];
    }

    public boolean add(Object o) {
        if (tail == capacity) {
            throw new RuntimeException("队列已满");
        }
        arr[tail] = o;
        tail++;
        return true;
    }

    public boolean remove(Object o) {
        return false;
    }

    public boolean addAll(Collection c) {
        return false;
    }

    public void clear() {

    }

    public boolean retainAll(Collection c) {
        return false;
    }

    public boolean removeAll(Collection c) {
        return false;
    }

    public boolean containsAll(Collection c) {
        return false;
    }

    public boolean offer(Object o) {
        return false;
    }

    public Object remove() {
        if (tail == head) {
            throw new RuntimeException("队列中没有元素");
        }
        Object obj = arr[tail - 1];
        tail--;
        return obj;
    }

    public Object poll() {
        if (head == tail) {
            throw new RuntimeException("队列中没有元素");
        }
        Object obj = arr[head];
        head++;
        return obj;
    }

    public Object element() {
        return null;
    }

    public Object peek() {
        if (head == tail) {
            return null;
        }
        return arr[head];
    }
}