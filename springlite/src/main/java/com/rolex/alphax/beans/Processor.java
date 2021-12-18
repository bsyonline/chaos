/*
 * Copyright (C) 2019 bsyonline
 */
package com.rolex.alphax.beans;

/**
 * @author rolex
 * @since 2019
 */
public interface Processor{
    
    /**
     * init()
     */
    void init();
    
    /**
     * process()
     */
    void process();
    
    /**
     * destroy()
     */
    void destroy();
    
}