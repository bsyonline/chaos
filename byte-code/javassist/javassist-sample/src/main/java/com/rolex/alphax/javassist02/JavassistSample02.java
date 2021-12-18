/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.javassist02;

import javassist.*;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 字节码生成属性和方法
 *
 * @author rolex
 * @since 2020
 */
public class JavassistSample02 {
    public static void main(String[] args) throws CannotCompileException, NotFoundException, IOException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        ClassPool classPool = ClassPool.getDefault();
        CtClass ctClass = classPool.makeClass("com.rolex.alphax.javassist02.EnhanceJavassistTest");
        /*
            属性字段
         */
        CtField ctField = new CtField(CtClass.intType, "a", ctClass);
        ctField.setModifiers(Modifier.PRIVATE + Modifier.STATIC + Modifier.FINAL);
        ctClass.addField(ctField, "10");

        /*
            方法：plus
         */
        CtMethod plus = new CtMethod(CtClass.intType, "plus", new CtClass[]{CtClass.intType, CtClass.intType}, ctClass);
        plus.setModifiers(Modifier.PUBLIC);
        plus.setBody("{return a + $1 + $2;}");
        ctClass.addMethod(plus);

        /*
            方法：divide
         */
        CtMethod divide = new CtMethod(CtClass.doubleType, "divide", new CtClass[]{CtClass.doubleType, CtClass.doubleType}, ctClass);
        divide.setModifiers(Modifier.PUBLIC);
        divide.setBody("{return $1 / $2;}");
        ctClass.addMethod(divide);

        /*
            输出
         */
        ctClass.writeFile("target/classes");

        Class clazz = ctClass.toClass();
        Object obj = clazz.newInstance();

        Method plusMethod = clazz.getDeclaredMethod("plus", new Class[]{int.class, int.class});
        Object o1 = plusMethod.invoke(obj, new Object[]{1, 3});
        System.out.println(o1);

        Method divideMethod = clazz.getDeclaredMethod("divide", new Class[]{double.class, double.class});
        Object o2 = divideMethod.invoke(obj, new Object[]{10D, 2D});
        System.out.println(o2);

    }
}
