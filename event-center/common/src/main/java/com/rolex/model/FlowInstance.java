package com.rolex.model;

import lombok.Data;

/**
 * @author rolex
 * @since 2023/7/1
 */
@Data
public class FlowInstance {
    Long id;
    String flowName;
    int status;
}
