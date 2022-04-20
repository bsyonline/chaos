package com.rolex.rpc.rebalance;

import com.rolex.rpc.model.ServerInfo;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
public interface Strategy {
    ServerInfo select();
}
