package com.rolex.alphax.test;

import java.lang.reflect.Field;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
public class Test6 {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        Class cache = Integer.class.getDeclaredClasses()[0];
        Field c = cache.getDeclaredField("cache");
        c.setAccessible(true);
        Integer[] array = (Integer[]) c.get(cache);
        // array[129] is 1
        array[130] = array[129];
        // Set 2 to be 1
        array[131] = array[129];
        // Set 3 to be 1
        Integer a = 1;
        if(a == (Integer)1 && a == (Integer)2 && a == (Integer)3){
            System.out.println("Success");
        }
    }
}
