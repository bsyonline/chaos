package com.rolex.alphax.behavioral.chainofresponsibility.linkedtable;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
public abstract class Handler {

    protected Handler handler;

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    protected final void next() {
        if (this.handler != null) {
            this.handler.handle();
        }
    }

    abstract void handle();
}
