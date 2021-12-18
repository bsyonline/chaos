/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.asm05;

import org.objectweb.asm.*;
import org.objectweb.asm.commons.AdviceAdapter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.objectweb.asm.Opcodes.ASM5;

/**
 * 字节码增强，调用其他类的方法
 *
 * @author rolex
 * @since 2020
 */
public class AsmSample5 extends ClassLoader {
    public static void main(String[] args) throws IOException, NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        ClassReader cr = new ClassReader(Calculator.class.getName());
        ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_MAXS);
        {
            MethodVisitor methodVisitor = cw.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);
            methodVisitor.visitVarInsn(Opcodes.ALOAD, 0);
            methodVisitor.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
            methodVisitor.visitInsn(Opcodes.RETURN);
            methodVisitor.visitMaxs(1, 1);
            methodVisitor.visitEnd();
        }
        ClassVisitor cv = new ProfilingClassAdapter(cw, Calculator.class.getSimpleName());
        cr.accept(cv, ClassReader.EXPAND_FRAMES);
        byte[] bytes = cw.toByteArray();
        outputClazz(bytes);
        Class<?> clazz = new AsmSample5().defineClass("com.rolex.alphax.asm05.Calculator", bytes, 0, bytes.length);
        Method add = clazz.getMethod("add", int.class, int.class);
        Object obj = add.invoke(clazz.newInstance(), 20, 5);
        System.out.println("测试结果：" + obj);
    }

    static class ProfilingClassAdapter extends ClassVisitor {
        public ProfilingClassAdapter(final ClassVisitor cv, String innerClassName) {
            super(ASM5, cv);
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
            System.out.println("access：" + access);
            System.out.println("name：" + name);
            System.out.println("desc：" + desc);
            if (!"add".equals(name)) {
                return null;
            }
            MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
            return new ProfilingMethodVisitor(mv, access, name, desc);
        }
    }

    static class ProfilingMethodVisitor extends AdviceAdapter {
        private String methodName = "";

        protected ProfilingMethodVisitor(MethodVisitor methodVisitor, int access, String name, String descriptor) {
            super(ASM5, methodVisitor, access, name, descriptor);
            this.methodName = name;
        }

        @Override
        protected void onMethodEnter() {
            // 输出方法和参数
            mv.visitLdcInsn(methodName);
            mv.visitInsn(ICONST_2);
            mv.visitIntInsn(NEWARRAY, T_INT);
            mv.visitInsn(DUP);
            mv.visitInsn(ICONST_0);
            mv.visitVarInsn(ILOAD, 1);
            mv.visitInsn(IASTORE);
            mv.visitInsn(DUP);
            mv.visitInsn(ICONST_1);
            mv.visitVarInsn(ILOAD, 2);
            mv.visitInsn(IASTORE);
            mv.visitMethodInsn(INVOKESTATIC, "com/rolex/alphax/asm05/Logger", "info", "(Ljava/lang/String;[I)V", false);
        }

        @Override
        protected void onMethodExit(int opcode) {
        }
    }

    private static void outputClazz(byte[] bytes) {
        // 输出类字节码
        FileOutputStream out = null;
        try {
            String pathName = AsmSample5.class.getResource("/").getPath() + "AsmCalculator.class";
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
}
