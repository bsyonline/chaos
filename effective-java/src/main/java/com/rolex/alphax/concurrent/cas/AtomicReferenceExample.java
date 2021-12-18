/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.concurrent.cas;

import java.util.concurrent.atomic.AtomicMarkableReference;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;
import java.util.concurrent.locks.LockSupport;

/**
 * @author rolex
 * @since 2020
 */
public class AtomicReferenceExample {
    private static final Integer A = 1000;
    private static final Integer B = 100;
    private static final Integer C = 200;

    private static AtomicReference atomicReference = new AtomicReference(A);
    private static AtomicStampedReference atomicStampedReference = new AtomicStampedReference(A, 1);
    private static AtomicMarkableReference atomicMarkableReference = new AtomicMarkableReference(A, false);

    public static void main(String[] args) throws InterruptedException {
        atomicReference();
        Thread.sleep(1000);
        System.out.println("--");
        atomicStampedReference();
        Thread.sleep(1000);
        System.out.println("--");
        atomicMarkableReference();
    }

    private static void atomicReference() {
        Thread t1 = new Thread(() -> {
            Object value = atomicReference.get();
            System.out.println(Thread.currentThread().getName() + "获取初始值: value=" + value);
            // 阻塞线程t1
            LockSupport.park();
            System.out.println("当前value=" + atomicReference.get());
            System.out.println("期望value=" + value);
            boolean a1 = atomicReference.compareAndSet(value, C);
            System.out.println(Thread.currentThread().getName() + "第一次修改: state= " + a1 + " value=" + atomicReference.get());
        }, "线程A");
        t1.start();

        Thread t2 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "获取初始值: value=" + atomicReference.get());
            // 第一次修改
            boolean b1 = atomicReference.compareAndSet(atomicReference.get(), B);
            System.out.println(Thread.currentThread().getName() + "第一次修改: state=" + b1 + " value=" + atomicReference.get());
            // 第二次修改
            boolean b2 = atomicReference.compareAndSet(atomicReference.get(), A);
            System.out.println(Thread.currentThread().getName() + "第二次修改: state=" + b2 + " value=" + atomicReference.get());
            // 唤醒线程t1
            LockSupport.unpark(t1);
        }, "线程B");
        t2.start();
    }

    private static void atomicStampedReference() {
        Thread t1 = new Thread(() -> {
            Object value = atomicStampedReference.getReference();
            int stamp = atomicStampedReference.getStamp();
            System.out.println(Thread.currentThread().getName() + "获取初始值: value=" + value + " stamp=" + stamp);
            // 阻塞线程t1
            LockSupport.park();
            System.out.println("当前value=" + atomicStampedReference.getReference() + " stamp=" + atomicStampedReference.getStamp());
            System.out.println("期望value=" + value + " stamp=" + stamp);
            boolean a1 = atomicStampedReference.compareAndSet(value, C, stamp, stamp + 1);
            System.out.println(Thread.currentThread().getName() + "第一次修改: state= " + a1 + " value=" + atomicStampedReference.getReference() + " stamp=" + atomicStampedReference.getStamp());
        }, "线程A");
        t1.start();

        Thread t2 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "获取初始值: value=" + atomicStampedReference.getReference() + " stamp=" + atomicStampedReference.getStamp());
            // 第一次修改
            boolean b1 = atomicStampedReference.compareAndSet(atomicStampedReference.getReference(), B, atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1);
            System.out.println(Thread.currentThread().getName() + "第一次修改: state=" + b1 + " value=" + atomicStampedReference.getReference() + " stamp=" + atomicStampedReference.getStamp());
            // 第二次修改
            boolean b2 = atomicStampedReference.compareAndSet(atomicStampedReference.getReference(), A, atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1);
            System.out.println(Thread.currentThread().getName() + "第二次修改: state=" + b2 + " value=" + atomicStampedReference.getReference() + " stamp=" + atomicStampedReference.getStamp());
            // 唤醒线程t1
            LockSupport.unpark(t1);
        }, "线程B");
        t2.start();
    }

    private static void atomicMarkableReference() {
        Thread t1 = new Thread(() -> {
            Object value = atomicMarkableReference.getReference();
            boolean marked = atomicMarkableReference.isMarked();
            System.out.println(Thread.currentThread().getName() + "获取初始值: value=" + value + " marked=" + marked);
            // 阻塞线程t1
            LockSupport.park();
            System.out.println("当前value=" + atomicMarkableReference.getReference() + " marked=" + atomicMarkableReference.isMarked());
            System.out.println("期望value=" + value + " marked=" + marked);
            boolean a1 = atomicMarkableReference.compareAndSet(value, C, marked, true);
            System.out.println(Thread.currentThread().getName() + "第一次修改: state= " + a1 + " value=" + atomicMarkableReference.getReference() + " marked=" + atomicMarkableReference.isMarked());
        }, "线程A");
        t1.start();

        Thread t2 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "获取初始值: value=" + atomicMarkableReference.getReference() + " marked=" + atomicMarkableReference.isMarked());
            // 第一次修改
            boolean b1 = atomicMarkableReference.compareAndSet(atomicMarkableReference.getReference(), A, atomicMarkableReference.isMarked(), true);
            System.out.println(Thread.currentThread().getName() + "第一次修改: state=" + b1 + " value=" + atomicMarkableReference.getReference() + " marked=" + atomicMarkableReference.isMarked());
            // 唤醒线程t1
            LockSupport.unpark(t1);
        }, "线程B");
        t2.start();
    }
}

class User {
    String name;
    Integer age;

    public User(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}