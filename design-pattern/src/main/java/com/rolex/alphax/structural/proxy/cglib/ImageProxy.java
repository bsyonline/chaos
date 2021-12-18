/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.structural.proxy.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author rolex
 * @since 2020
 */
public class ImageProxy implements MethodInterceptor,Advice {

    private GifImage target;

    public ImageProxy(GifImage target) {
        this.target = target;
    }

    public Object getProxyInstance() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(target.getClass());
        enhancer.setCallback(this);
        return enhancer.create(new Class[]{String.class}, new Object[]{target.imageFilePath});
    }

    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        before();
        Object obj = methodProxy.invokeSuper(o, args);
        after();
        return obj;
    }

    @Override
    public void before() {
        System.out.println("before");
    }

    @Override
    public void after() {
        System.out.println("after");
    }
}
