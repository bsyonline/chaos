package com.rolex.alphax.behavioral.mediator;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
public class CustomerA extends Customer {

    Mediator mediator;

    public CustomerA(Mediator mediator) {
        this.mediator = mediator;
    }

    void send(String msg) {
        System.out.println("A: 发布房源”" + msg + "”");
        mediator.notify(this, msg);
    }

    public void receive(String msg) {
        System.out.println("A: 收到消息“" + msg + "”");
    }
}
