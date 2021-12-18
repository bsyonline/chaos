/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.structural.proxy.dynamic;

/**
 * @author rolex
 * @since 2020
 */
public class App {
    public static void main(String[] args) {
        Image pngImage = new PngImage("photo1.png");
        Image imageProxy = (Image) new ImageProxy(pngImage).getProxyInstance();
        imageProxy.display();
    }
}