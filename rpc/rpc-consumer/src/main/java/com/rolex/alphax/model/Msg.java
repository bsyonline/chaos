/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author rolex
 * @since 2020
 */
@Data
@AllArgsConstructor
public class Msg {

    int length;
    byte[] content;

}
