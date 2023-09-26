package com.rolex.model;

import lombok.Data;

/**
 * @author rolex
 * @since 2023/7/1
 */
@Data
public class JobInstance {
    Long id;
    String jobName;
    int status;
}
