/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.asm01;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * 字节码生成 HelloWorld
 *
 * @author rolex
 * @since 2020
 */
public class AsmSample1 extends ClassLoader {

    public static void main(String[] args) throws Exception {
        // 生成二进制字节码
        byte[] bytes = generate();
        // 输出字节码
        outputClazz(bytes);
        // 加载AsmHelloWorld
        Class<?> clazz = new AsmSample1().defineClass("com.rolex.alphax.asm01.AsmHelloWorld", bytes, 0, bytes.length);
        // 反射获取 main 方法
        Method main = clazz.getMethod("main", String[].class);
        // 调用 main 方法
        main.invoke(null, new Object[]{new String[]{}});
    }

    private static void outputClazz(byte[] bytes) {
        // 输出类字节码
        FileOutputStream out = null;
        try {
            String pathName = AsmSample1.class.getResource("/").getPath() + "AsmHelloWorld.class";
            out = new FileOutputStream(new File(pathName));
            System.out.println("ASM类输出路径：" + pathName);
            out.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != out) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static byte[] generate() {
        ClassWriter classWriter = new ClassWriter(0);
        /*
            定义对象头
         */
        classWriter.visit(Opcodes.V1_8/*版本号*/,
                Opcodes.ACC_PUBLIC/*修饰符*/,
                "com/rolex/alphax/asm01/AsmHelloWorld"/*类全名*/,
                null/*签名*/,
                "java/lang/Object"/*父类*/,
                null/*接口*/);
        /*
            添加方法
         */
        MethodVisitor methodVisitor = classWriter.visitMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC/*修饰符*/,
                "main"/*方法名*/,
                "([Ljava/lang/String;)V"/*描述符*/,
                null/*签名*/,
                null/*异常*/);
        /*
            执行指令
         */
        methodVisitor.visitFieldInsn(Opcodes.GETSTATIC,
                "java/lang/System",
                "out",
                "Ljava/io/PrintStream;");
        /*
            加载常量
         */
        methodVisitor.visitLdcInsn("Hello World");
        /*
            调用方法
        */
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL,
                "java/io/PrintStream",
                "println",
                "(Ljava/lang/String;)V",
                false);
        /*
            返回
        */
        methodVisitor.visitInsn(Opcodes.RETURN);

        methodVisitor.visitMaxs(2/*操作数栈的深度*/, 1/*局部变量的大小*/);

        methodVisitor.visitEnd();

        return classWriter.toByteArray();
    }

}
