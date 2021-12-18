/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.javassist01;

import javassist.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author rolex
 * @since 2020
 */
public class JavassistSample01 {
    public static void main(String[] args) throws NotFoundException, CannotCompileException, IOException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        ClassPool classPool = ClassPool.getDefault();
        /*
            创建类
         */
        CtClass ctClass = classPool.makeClass("com.rolex.alphax.javassist01.HelloWorld");
        /*
            添加方法
         */
        CtMethod ctMethod = new CtMethod(CtClass.voidType, "main", new CtClass[]{classPool.get(String[].class.getName())}, ctClass);
        ctMethod.setModifiers(Modifier.PUBLIC + Modifier.STATIC);
        ctMethod.setBody("{System.out.println(\"Hello World\");}");

        ctClass.addMethod(ctMethod);
        /*
            创建构造方法
         */
        CtConstructor ctConstructor = new CtConstructor(new CtClass[]{}, ctClass);
        ctConstructor.setBody("{}");
        ctClass.addConstructor(ctConstructor);
        /*
            输出类
         */
        ctClass.writeFile("target/classes");

        Class clazz = ctClass.toClass();
        Object o = clazz.newInstance();

        Method method = clazz.getDeclaredMethod("main", String[].class);
        method.invoke(o, (Object) new String[1]);

    }
}
