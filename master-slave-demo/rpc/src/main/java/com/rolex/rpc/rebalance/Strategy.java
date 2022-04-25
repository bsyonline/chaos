package com.rolex.rpc.rebalance;

import com.rolex.discovery.routing.Host;

import java.util.List;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
public interface Strategy {
    Host select(List<Host> servers);
}
