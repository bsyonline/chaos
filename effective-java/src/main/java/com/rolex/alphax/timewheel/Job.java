/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.timewheel;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author rolex
 * @since 2020
 */
@Data
@AllArgsConstructor
public class Job {
    long id;
    String name;
    long createTime;

    @Override
    public String toString() {
        return "Job{" +
                "id=" + id +
                '}';
    }
}
