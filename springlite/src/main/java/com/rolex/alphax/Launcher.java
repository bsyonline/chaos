/*
 * Copyright (C) 2019 bsyonline
 */
package com.rolex.alphax;

import com.rolex.alphax.factory.ApplicationContext;
import com.rolex.alphax.stereotype.ComponentScan;

/**
 * @author rolex
 * @since 2019
 */
@ComponentScan(basePackages = "com.rolex.alphax.service")
public class Launcher {
    public static void main(String[] args) {
        ApplicationContext.run(Launcher.class, args);
    }
}
