/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.structural.proxy.dynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author rolex
 * @since 2020
 */
public class ImageProxy implements Advice{

    private Image target;

    public ImageProxy(Image target) {
        this.target = target;
    }

    public Object getProxyInstance() {
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                before();
                Object obj = method.invoke(target, args);
                after();
                return obj;
            }
        });
    }

    @Override
    public void before(){
        System.out.println("before");
    }

    @Override
    public void after(){
        System.out.println("after");
    }
}
