/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.asm06;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.commons.AdviceAdapter;

/**
 * @author rolex
 * @since 2020
 */
public class ProfilingMethodVisitor extends AdviceAdapter {
    protected ProfilingMethodVisitor(int api, MethodVisitor methodVisitor, int access, String name, String descriptor) {
        super(api, methodVisitor, access, name, descriptor);
    }

    @Override
    protected void onMethodEnter() {

    }
}
