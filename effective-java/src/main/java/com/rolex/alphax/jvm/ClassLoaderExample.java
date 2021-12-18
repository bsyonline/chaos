/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.jvm;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author rolex
 * @since 2020
 */
public class ClassLoaderExample {
    public static void main(String[] args) throws Exception {
        ClassLoader myLoader = new ClassLoader() {
            @Override
            public Class<?> loadClass(String name) throws ClassNotFoundException {
                try {
                    String fileName = name.substring(name.lastIndexOf(".") + 1) + ".class";
                    InputStream is = getClass().getResourceAsStream(fileName);
                    if (is == null) {
                        return super.loadClass(name);
                    }
                    byte[] b = new byte[is.available()];
                    is.read(b);
                    return defineClass(name, b, 0, b.length);
                } catch (IOException e) {
                    throw new ClassNotFoundException(name);
                }
            }
        };
        Object obj = myLoader.loadClass("com.rolex.alphax.jvm.ClassLoaderExample").newInstance();
        System.out.println(obj.getClass());
        /*
            obj 是由myLoader加载的类，com.rolex.alphax.jvm.ClassLoaderExample是由系统的classloader加载的类
            相同的类文件由不同的classloader加载，class 是不同的
         */
        System.out.println(obj instanceof com.rolex.alphax.jvm.ClassLoaderExample);
    }
}
