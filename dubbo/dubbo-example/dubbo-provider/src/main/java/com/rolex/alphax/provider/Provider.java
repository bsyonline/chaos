/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.provider;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author rolex
 * @since 2020
 */
public class Provider {

    public static void main(String[] args) throws Exception {
        System.setProperty("java.net.preferIPv4Stack", "true");
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"classpath:/provider.xml"});
        context.start();
        System.out.println("Provider started.");
        System.in.read(); // press any key to exit
    }
}
