package com.rolex.slave.rpc;

import com.rolex.slave.model.ServerInfo;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
public interface ServerSelectorStrategy {
    ServerInfo selectServer();
}
