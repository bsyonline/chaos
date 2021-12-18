package com.rolex.spi.impl;

import com.rolex.spi.HelloSpi;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
public class Chinese implements HelloSpi {

    @Override
    public void sayHello() {
        System.out.println("你好");
    }
}
