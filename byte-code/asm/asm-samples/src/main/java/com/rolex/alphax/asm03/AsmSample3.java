/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.asm03;

import org.objectweb.asm.*;
import org.objectweb.asm.commons.AdviceAdapter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.objectweb.asm.Opcodes.ASM5;

/**
 * 字节码增强方法，给方法增加耗时统计
 *
 * @author rolex
 * @since 2020
 */
public class AsmSample3 extends ClassLoader {
    public static void main(String[] args) throws IOException, NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        ClassReader cr = new ClassReader(User.class.getName());
        ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_MAXS);
        {
            MethodVisitor methodVisitor = cw.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);
            methodVisitor.visitVarInsn(Opcodes.ALOAD, 0);
            methodVisitor.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
            methodVisitor.visitInsn(Opcodes.RETURN);
            methodVisitor.visitMaxs(1, 1);
            methodVisitor.visitEnd();
        }
        ClassVisitor cv = new ProfilingClassAdapter(cw, User.class.getSimpleName());
        cr.accept(cv, ClassReader.EXPAND_FRAMES);
        byte[] bytes = cw.toByteArray();
        outputClazz(bytes);
        Class<?> clazz = new AsmSample3().defineClass("com.rolex.alphax.asm03.User", bytes, 0, bytes.length);
        Method queryUserInfo = clazz.getMethod("queryById", String.class);
        Object obj = queryUserInfo.invoke(clazz.newInstance(), "10001");
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
            if (!"queryById".equals(name)) {
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
            mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "nanoTime", "()J", false);
            mv.visitVarInsn(LSTORE, 2);
            mv.visitVarInsn(ALOAD, 1);
        }

        @Override
        protected void onMethodExit(int opcode) {
            if ((IRETURN <= opcode && opcode <= RETURN) || opcode == ATHROW) {
                mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
                mv.visitTypeInsn(NEW, "java/lang/StringBuilder");
                mv.visitInsn(DUP);
                mv.visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false);
                mv.visitLdcInsn("方法执行耗时(纳秒)->" + methodName + "：");
                mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
                mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "nanoTime", "()J", false);
                mv.visitVarInsn(LLOAD, 2);
                mv.visitInsn(LSUB);
                mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(J)Ljava/lang/StringBuilder;", false);
                mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
                mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
            }
        }
    }

    private static void outputClazz(byte[] bytes) {
        // 输出类字节码
        FileOutputStream out = null;
        try {
            String pathName = AsmSample3.class.getResource("/").getPath() + "AsmUser.class";
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
