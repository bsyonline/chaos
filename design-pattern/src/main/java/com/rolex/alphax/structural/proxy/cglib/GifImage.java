/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.structural.proxy.cglib;

/**
 * @author rolex
 * @since 2020
 */
public class GifImage {
    String imageFilePath;

    public GifImage(String imageFilePath) {
        this.imageFilePath = imageFilePath;
    }

    public void display() {
        System.out.println("display image " + imageFilePath);
    }
}
