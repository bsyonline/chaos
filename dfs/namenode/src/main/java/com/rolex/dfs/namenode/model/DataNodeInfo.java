package com.rolex.dfs.namenode.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
@Data
public class DataNodeInfo {
    private String ip;
    private String hostname;
    private long latestHeartbeatTime = System.currentTimeMillis();

    public DataNodeInfo(String ip, String hostname) {
        this.ip = ip;
        this.hostname = hostname;
    }
}
