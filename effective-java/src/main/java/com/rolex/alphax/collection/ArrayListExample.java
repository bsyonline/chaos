/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.collection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * @author rolex
 * @since 2020
 */
public class ArrayListExample {
    public static void main(String[] args) {
        List list = new ArrayList(10);
        list.add(1);
        list.add(2);
        list.add(3);

        //1
        for (Object o : list) {
            System.out.println(o);
        }

        //2
        list.forEach(i -> {
            System.out.println(i);
        });

        //3
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }

        //4
        Iterator iterator = list.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

        //5
        ListIterator listIterator = list.listIterator();
        while (listIterator.hasNext()) {
            System.out.println(listIterator.next());
        }

        //6
        Object[] arr = list.toArray();
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }


        /**
         * 会报 java.util.ConcurrentModificationException
         */
        for(Object o : list){
            list.remove(o);
        }

        Iterator it = list.iterator();
        while (it.hasNext()){
            it.next();
            it.remove();
        }
        System.out.println(list);


    }
}
