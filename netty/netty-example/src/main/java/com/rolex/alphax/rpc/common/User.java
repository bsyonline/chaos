/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.rpc.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author rolex
 * @since 2020
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {
    int id;
    String name;
    int age;
}
