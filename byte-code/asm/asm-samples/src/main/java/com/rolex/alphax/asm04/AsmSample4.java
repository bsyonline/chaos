/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.asm04;

import org.objectweb.asm.*;
import org.objectweb.asm.commons.AdviceAdapter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.objectweb.asm.Opcodes.ASM5;

/**
 * 字节码增强，获取方法的信息
 *
 * @author rolex
 * @since 2020
 */
public class AsmSample4 extends ClassLoader {
    public static void main(String[] args) throws IOException, NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        ClassReader cr = new ClassReader(Student.class.getName());
        ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_MAXS);
        {
            MethodVisitor methodVisitor = cw.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);
            methodVisitor.visitVarInsn(Opcodes.ALOAD, 0);
            methodVisitor.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
            methodVisitor.visitInsn(Opcodes.RETURN);
            methodVisitor.visitMaxs(1, 1);
            methodVisitor.visitEnd();
        }
        ClassVisitor cv = new ProfilingClassAdapter(cw, Student.class.getSimpleName());
        cr.accept(cv, ClassReader.EXPAND_FRAMES);
        byte[] bytes = cw.toByteArray();
        outputClazz(bytes);
        Class<?> clazz = new AsmSample4().defineClass("com.rolex.alphax.asm04.Student", bytes, 0, bytes.length);
        Method getName = clazz.getMethod("getName", String.class);
        Object obj = getName.invoke(clazz.newInstance(), "tom");
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
            if (!"getName".equals(name)) {
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
            mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            mv.visitVarInsn(ALOAD, 1);
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
        }

        @Override
        protected void onMethodExit(int opcode) {
        }
    }

    private static void outputClazz(byte[] bytes) {
        // 输出类字节码
        FileOutputStream out = null;
        try {
            String pathName = AsmSample4.class.getResource("/").getPath() + "AsmStudent.class";
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
