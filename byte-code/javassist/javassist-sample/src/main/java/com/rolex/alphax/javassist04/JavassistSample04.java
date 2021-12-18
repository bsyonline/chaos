/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.javassist04;

import javassist.*;
import javassist.bytecode.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 通过指令修改方法返回值
 *
 * @author rolex
 * @since 2020
 */
public class JavassistSample04 extends ClassLoader {
    public static void main(String[] args) throws NotFoundException, CannotCompileException, IOException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException, BadBytecode {
        ClassPool classPool = ClassPool.getDefault();
        CtClass ctClass = classPool.get(JavassistTest04.class.getName());
        CtMethod ctMethod = ctClass.getDeclaredMethod("foo");
        /*
            获取指令码
         */
        MethodInfo methodInfo = ctMethod.getMethodInfo();
        CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
        CodeIterator iterator = codeAttribute.iterator();
        while (iterator.hasNext()) {
            int idx = iterator.next();
            int code = iterator.byteAt(idx);
            System.out.println("指令码:" + idx + " > " + code +" - "+ Mnemonic.OPCODE[code]);
        }

        ConstPool constPool = methodInfo.getConstPool();
        Bytecode bytecode = new Bytecode(constPool);
        bytecode.addIconst(0);
        bytecode.addReturn(CtClass.intType);

        methodInfo.setCodeAttribute(bytecode.toCodeAttribute());

        ctClass.writeFile("target/classes");

        byte[] bytes = ctClass.toBytecode();
        Class<?> clazz = new JavassistSample04().defineClass("com.rolex.alphax.javassist04.JavassistTest04",
                bytes, 0, bytes.length);

        Object obj = clazz.newInstance();

        Method helloMethod = clazz.getDeclaredMethod("foo", new Class[]{});
        Object o = helloMethod.invoke(obj, new Object[]{});
        System.out.println(o);
    }
}
