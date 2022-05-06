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
public class JobInstance {
    long id;
    String tenantCode;
    long executorGroupId;
}
