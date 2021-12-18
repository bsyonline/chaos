package com.rolex.alphax.behavioral.chainofresponsibility.linkedtable;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
public class App {
    public static void main(String[] args) {
        Handler bodyHandler = new HttpBodyHandler();
        Handler headerHandler = new HttpHeaderHandler();
        headerHandler.setHandler(bodyHandler);
        headerHandler.handle();
    }
}
