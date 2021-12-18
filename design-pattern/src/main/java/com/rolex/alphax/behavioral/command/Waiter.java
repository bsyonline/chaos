package com.rolex.alphax.behavioral.command;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
public class Waiter {

    private Command command;

    public void setCommand(Command command) {
        this.command = command;
    }

    public void orderUp() {
        command.execute();
    }
}
