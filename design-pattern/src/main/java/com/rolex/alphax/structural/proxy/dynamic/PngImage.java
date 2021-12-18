/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.structural.proxy.dynamic;

/**
 * @author rolex
 * @since 2020
 */
public class PngImage implements Image {
    private String imageFilePath;

    public PngImage(String imageFilePath) {
        this.imageFilePath = imageFilePath;
    }

    @Override
    public void display() {
        System.out.println("display image " + imageFilePath);
    }
}
