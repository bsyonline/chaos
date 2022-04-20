package com.rolex.master.service;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
public interface DispatcherService {
    void dispatch(String msg) throws InterruptedException;
}
