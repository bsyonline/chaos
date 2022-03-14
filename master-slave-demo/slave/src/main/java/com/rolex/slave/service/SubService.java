package com.rolex.slave.service;


import com.rolex.common.model.RouteInfo;

import java.util.Map;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
public interface SubService {

    void receiveMessage(String message);
    Map<String, RouteInfo> getRegistry();
}