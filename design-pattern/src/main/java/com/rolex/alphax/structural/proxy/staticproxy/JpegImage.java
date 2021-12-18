/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.structural.proxy.staticproxy;

/**
 * @author rolex
 * @since 2020
 */
public class JpegImage implements Image {
    private String imageFilePath;

    public JpegImage(String imageFilePath) {
        this.imageFilePath = imageFilePath;
    }

    @Override
    public void display() {
        System.out.println("display image " + imageFilePath);
    }
}
