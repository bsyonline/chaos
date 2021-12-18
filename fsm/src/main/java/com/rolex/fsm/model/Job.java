package com.rolex.fsm.model;

import com.rolex.fsm.config.JobState;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
@Data
public class Job {
    public Job(Long id, JobState state, LocalDateTime createTime) {
        this.id = id;
        this.state = state;
        this.createTime = createTime;
    }

    Long id;
    JobState state;
    LocalDateTime createTime;
}
