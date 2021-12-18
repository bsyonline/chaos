package com.rolex.fsm.config;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
public enum JobEvent {
    READY(1),
    DISPATCH(2),
    RUN(3),
    KILL(4),
    FINISH(5),
    CANCEL(6);

    int code;

    JobEvent(int code) {
        this.code = code;
    }
}
