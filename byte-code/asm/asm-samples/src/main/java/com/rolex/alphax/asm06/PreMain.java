/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.asm06;

import java.lang.instrument.Instrumentation;

/**
 * @author rolex
 * @since 2020
 */
public class PreMain {
    //JVM 首先尝试在代理类上调用以下方法
    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("====java agent");
//        inst.addTransformer(new ProfilingTransformer());
    }

    //如果代理类没有实现上面的方法，那么 JVM 将尝试调用该方法
    public static void premain(String agentArgs) {
    }
}
