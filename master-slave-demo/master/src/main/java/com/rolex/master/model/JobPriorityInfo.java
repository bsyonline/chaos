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
public class JobPriorityInfo {
    private long jobInstId;
    String tenantCode;
    long executorGroupId;
}
