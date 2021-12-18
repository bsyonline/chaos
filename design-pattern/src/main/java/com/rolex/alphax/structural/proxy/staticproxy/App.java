/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.structural.proxy.staticproxy;

/**
 * @author rolex
 * @since 2020
 */
public class App {
    public static void main(String[] args) {
		ImageProxy imageProxy = new ImageProxy("photo1.jpeg");
		imageProxy.display();
    }
}