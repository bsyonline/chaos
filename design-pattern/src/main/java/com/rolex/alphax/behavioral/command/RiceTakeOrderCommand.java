package com.rolex.alphax.behavioral.command;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
public class RiceTakeOrderCommand implements Command {

    private Cook cook;

    public RiceTakeOrderCommand(Cook cook) {
        this.cook = cook;
    }

    @Override
    public void execute() {
        cook.make("米饭");
    }
}
