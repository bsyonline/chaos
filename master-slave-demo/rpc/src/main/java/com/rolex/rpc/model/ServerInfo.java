package com.rolex.rpc.model;

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
public class ServerInfo {
    String host;
    int port;
}
