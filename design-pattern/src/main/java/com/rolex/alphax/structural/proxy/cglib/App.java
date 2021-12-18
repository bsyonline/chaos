/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.structural.proxy.cglib;

/**
 * @author rolex
 * @since 2020
 */
public class App {
    public static void main(String[] args) {
        GifImage gifImage = new GifImage("photo1.gif");
        GifImage imageProxy = (GifImage) new ImageProxy(gifImage).getProxyInstance();
		imageProxy.display();
    }
}