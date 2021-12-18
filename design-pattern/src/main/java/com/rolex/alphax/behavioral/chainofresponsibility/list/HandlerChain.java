/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.behavioral.chainofresponsibility.list;

import java.util.ArrayList;
import java.util.List;

/**
 * @author rolex
 * @since 2020
 */
public class HandlerChain {
    List<Handler> handlers = new ArrayList<>();

    public void addHandler(Handler handler) {
        handlers.add(handler);
    }

    public void handle() {
        for (Handler handler : handlers) {
            handler.handle();
        }
    }
}
