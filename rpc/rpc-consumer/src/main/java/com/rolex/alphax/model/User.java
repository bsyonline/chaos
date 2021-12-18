/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author rolex
 * @since 2020
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    int id;
    String name;
    int age;
}
