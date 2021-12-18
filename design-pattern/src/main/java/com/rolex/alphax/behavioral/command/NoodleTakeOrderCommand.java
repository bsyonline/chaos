package com.rolex.alphax.behavioral.command;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
public class NoodleTakeOrderCommand implements Command {

    private Cook cook;

    public NoodleTakeOrderCommand(Cook cook) {
        this.cook = cook;
    }

    @Override
    public void execute() {
        cook.make("面条");
    }
}
