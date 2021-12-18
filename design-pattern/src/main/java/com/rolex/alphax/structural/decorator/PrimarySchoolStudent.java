/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.structural.decorator;

import lombok.extern.slf4j.Slf4j;

/**
 * @author rolex
 * @since 2020
 */
@Slf4j
public class PrimarySchoolStudent implements Student{
    @Override
    public void exam() {
        log.info("小学考试");
    }
}
