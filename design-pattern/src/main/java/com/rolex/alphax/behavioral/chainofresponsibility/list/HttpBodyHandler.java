/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.behavioral.chainofresponsibility.list;

import lombok.extern.slf4j.Slf4j;

/**
 * @author rolex
 * @since 2020
 */
@Slf4j
public class HttpBodyHandler implements Handler {

    @Override
    public void handle() {
        log.info("wrapper http body");
    }
}
