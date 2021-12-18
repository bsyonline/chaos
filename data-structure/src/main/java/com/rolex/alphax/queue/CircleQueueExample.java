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
public class CircleQueueExample {
    public static void main(String[] args) {
        CircleQueue arrayQueue = new CircleQueue(5);
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
//        System.out.printf("删除队列元素：%d,队首元素为：%d\n", arrayQueue.remove(), arrayQueue.peek());
//        System.out.printf("删除队列元素：%d,队首元素为：%d\n", arrayQueue.remove(), arrayQueue.peek());
        System.out.printf("删除队列元素：%d,队首元素为：%d\n", arrayQueue.remove(), arrayQueue.peek());
        System.out.printf("删除队列元素：%d,队首元素为：%d\n", arrayQueue.remove(), arrayQueue.peek());
        System.out.printf("删除队列元素：%d,队首元素为：%d\n", arrayQueue.remove(), arrayQueue.peek());

        arrayQueue.poll();
        arrayQueue.add(6);
        System.out.printf("添加元素%d,队列大小为%d\n", 6, arrayQueue.size());
    }
}

class CircleQueue implements Queue {
    int capacity;// 容量
    int head;// 队列头位置
    int tail;// 队列尾位置
    int size;
    Object[] arr;// 存储队列元素的数组

    public CircleQueue(int capacity) {
        this.capacity = capacity;
        head = 0;
        tail = 0;
        size = 0;
        arr = new Object[capacity];
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return head == tail;
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
        if (size() == capacity) {
            throw new RuntimeException("队列已满");
        }
        arr[tail] = o;
        tail = (tail + 1) % capacity;
        size++;
        System.out.printf("add %d,head=%d,tail=%d,size=%d\n", o, head, tail, size);
        return false;
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
        if (size() == 0) {
            throw new RuntimeException("队列中没有元素");
        }
        Object obj = arr[tail];
        tail = (tail + capacity - 1) % capacity;
        size--;
        System.out.printf("remove tail,head=%d,tail=%d,size=%d\n", head, tail, size);
        return obj;
    }

    public Object poll() {
        if (size() == 0) {
            throw new RuntimeException("队列中没有元素");
        }
        head = (head == capacity) ? head = 0 : head + 1;
        Object obj = arr[head];
        head = (head + 1) % capacity;
        size--;
        System.out.printf("poll head,head=%d,tail=%d,size=%d\n", head, tail, size);
        return obj;
    }

    public Object element() {
        return null;
    }

    public Object peek() {
        if (size() == 0) {
            return null;
        }
        return arr[head];
    }
}
