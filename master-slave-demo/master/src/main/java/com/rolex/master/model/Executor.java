package com.rolex.master.model;

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
public class Executor {
    String host;
    int port;
    int type;
    String tenantCode;
    int executorGroupId;
}
