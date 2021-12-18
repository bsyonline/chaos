/*
 * Copyright (C) 2019 bsyonline
 */
package com.rolex.alphax.service;

import com.rolex.alphax.beans.GenericProcessor;
import com.rolex.alphax.stereotype.Autowired;
import com.rolex.alphax.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

/**
 * @author rolex
 * @since 2019
 */
@Component
@Slf4j
public class TestProcessor extends GenericProcessor {
    
    @Autowired
    TestConverter testConverter;
    
    @Override
    public void init() {
        log.info("test process inited");
    }
    
    @Override
    public void process() {
        testConverter.convert();
        log.info("test processing");
    }
    
    @Override
    public void destroy() {
        log.info("test process destroyed");
    }
}
