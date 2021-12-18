/*
 * Copyright (C) 2020 bsyonline
 *//*

package com.rolex.alphax.asm06;

import com.rolex.alphax.asm05.AsmSample5;
import org.objectweb.asm.*;
import org.objectweb.asm.commons.AdviceAdapter;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.objectweb.asm.Opcodes.ASM5;

*/
/**
 * @author rolex
 * @since 2020
 *//*

public class ProfilingTransformer implements ClassFileTransformer {
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        System.out.println("===" + className);
        try {
            return getBytes(loader, className, classfileBuffer);
        } catch (Throwable e) {
            System.out.println(e.getMessage());
        }
        return classfileBuffer;
    }

    static class ProfilingClassAdapter extends ClassVisitor {
        public ProfilingClassAdapter(final ClassVisitor cv, String innerClassName) {
            super(ASM5, cv);
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
            if (!"hello".equals(name)) {
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

            Matcher m = Pattern.compile("(L.*?;|\\[{0,2}L.*?;|[ZCBSIFJD]|\\[{0,2} [ZCBSIFJD]{1})").matcher(desc.substring(0, desc.lastIndexOf(')') + 1));
            while (m.find()) {
                parameterTypeList.add(m.group(1));
            }

            methodId = ProfilingAspect.generateMethodId(new MethodTag(fullClassName, simpleClassName, methodName, desc, parameterTypeList, desc.substring(desc.lastIndexOf(')') + 1)));
        }


        @Override
        protected void onMethodEnter() {
            mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "nanoTime", "()J", false);
            int startTimeIdentifier = newLocal(Type.LONG_TYPE);
            mv.visitVarInsn(LSTORE, startTimeIdentifier);

            if (parameterCount >= 4) {
                mv.visitVarInsn(BIPUSH, parameterCount);
                //初始化数组长度
            } else {
                switch (parameterCount) {
                    case 1:
                        mv.visitInsn(ICONST_1);
                        break;
                    case 2:
                        mv.visitInsn(ICONST_2);
                        break;
                    case 3:
                        mv.visitInsn(ICONST_3);
                        break;
                    default:
                        mv.visitInsn(ICONST_0);
                }
            }
            mv.visitTypeInsn(ANEWARRAY, Type.getDescriptor(Object.class));

        }

        @Override
        protected void onMethodExit(int opcode) {
        }
    }

    private byte[] getBytes(ClassLoader loader, String className, byte[] classfileBuffer) {
        ClassReader cr = new ClassReader(classfileBuffer);
        ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_MAXS);
        ClassVisitor cv = new ProfilingClassAdapter(cw, className);
        cr.accept(cv, ClassReader.EXPAND_FRAMES);
        return cw.toByteArray();
    }
}
*/
