package com.rolex.master.service;

import com.rolex.master.model.Executor;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
public interface ExecutorService {
    Executor findByIpPort(String host, int port);
}
