/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.service.impl;

import com.rolex.alphax.service.DemoService;

/**
 * @author rolex
 * @since 2020
 */
public class DemoServiceImpl implements DemoService {
    public String sayHello(String name) {
        return "Hello " + name;
    }
}
