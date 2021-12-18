/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.consumer;

import com.rolex.alphax.service.DemoService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author rolex
 * @since 2020
 */
public class Consumer {
    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"consumer.xml"});
        context.start();
        // Obtaining a remote service proxy
        DemoService demoService = (DemoService) context.getBean("demoService");
        for (int i = 1; i < Integer.MAX_VALUE; i++) {
            // Executing remote methods
            String hello = demoService.sayHello("world" + i);
            // Display the call result
            System.out.println(hello);
            Thread.sleep(1000);
        }
    }
}
