/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.model;

import lombok.Data;
import net.bytebuddy.implementation.bind.annotation.AllArguments;


/**
 * @author rolex
 * @since 2020
 */
@Data
public class Response {
    int code;
    String msg;
    Object result;

    public Response(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Response(int code, String msg, Object result) {
        this.code = code;
        this.msg = msg;
        this.result = result;
    }
}
