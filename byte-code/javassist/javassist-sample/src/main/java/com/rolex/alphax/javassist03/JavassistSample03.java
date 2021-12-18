/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.javassist03;

import javassist.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 字节码增强，替换返回值
 *
 * @author rolex
 * @since 2020
 */
public class JavassistSample03 extends ClassLoader {
    public static void main(String[] args) throws NotFoundException, CannotCompileException, IOException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        ClassPool classPool = ClassPool.getDefault();
        CtClass ctClass = classPool.get(JavassistTest03.class.getName());
        CtMethod hello = ctClass.getDeclaredMethod("hello");
        hello.setBody("{return \"world\";}");

        ctClass.writeFile("target/classes");

        byte[] bytes = ctClass.toBytecode();
        Class<?> clazz = new JavassistSample03().defineClass("com.rolex.alphax.javassist03.JavassistTest03",
                bytes, 0, bytes.length);

        Object obj = clazz.newInstance();

        Method helloMethod = clazz.getDeclaredMethod("hello", new Class[]{});
        Object o = helloMethod.invoke(obj, new Object[]{});
        System.out.println(o);
    }
}
