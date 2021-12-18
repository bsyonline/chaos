/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.behavioral.visitor;

/**
 * @author rolex
 * @since 2020
 */
public interface ResourceFile {

    void accept(Visitor visitor);
}

