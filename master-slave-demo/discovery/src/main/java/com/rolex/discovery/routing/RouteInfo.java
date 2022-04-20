package com.rolex.discovery.routing;

import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class RouteInfo {
    int nodeId;
    String ip;
    int port;
    String type;
    long timestamp;
    String connectedServer;
}
