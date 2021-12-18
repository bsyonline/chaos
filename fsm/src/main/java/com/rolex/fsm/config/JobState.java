package com.rolex.fsm.config;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
public enum JobState {
    START(0),
    READY(1),
    DISPATCHED(2),
    RUNNING(3),
    FINISHED(4),
    KILLED(5),
    CANCEL(6);

    int code;

    JobState(int code) {
        this.code = code;
    }
}
