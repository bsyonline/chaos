/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.behavioral.chainofresponsibility.list;

/**
 * @author rolex
 * @since 2020
 */
public class App {
    public static void main(String[] args) {
        HandlerChain chain = new HandlerChain();
        chain.addHandler(new HttpHeaderHandler());
        chain.addHandler(new HttpBodyHandler());
        chain.handle();
    }
}
