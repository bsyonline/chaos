package com.rolex.discovery.routing;

import lombok.Builder;
import lombok.Data;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
@Data
@Builder
public class LocalRoutingInfo {
    String host;
    int port;
    NodeState state;
}
