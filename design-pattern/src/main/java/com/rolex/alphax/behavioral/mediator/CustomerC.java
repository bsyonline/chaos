package com.rolex.alphax.behavioral.mediator;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
public class CustomerC extends Customer {

    Mediator mediator;

    public CustomerC(Mediator mediator) {
        this.mediator = mediator;
    }

    void send(String msg) {
        System.out.println("C: 发送请求" + "”");
        mediator.notify(this, msg);
    }

    public void receive(String msg) {
        System.out.println("C: 收到消息”" + msg + "”");
    }

}
