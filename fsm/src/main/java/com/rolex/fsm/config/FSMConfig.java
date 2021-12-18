package com.rolex.fsm.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.util.EnumSet;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
@Configuration
@EnableStateMachine
public class FSMConfig extends EnumStateMachineConfigurerAdapter<JobState, JobEvent> {
    @Override
    public void configure(StateMachineStateConfigurer<JobState, JobEvent> states) throws Exception {
        states.withStates().initial(JobState.START).states(EnumSet.allOf(JobState.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<JobState, JobEvent> transitions) throws Exception {
        transitions.withExternal()
                .source(JobState.START).target(JobState.READY).event(JobEvent.READY)
                .and()
                .withExternal()
                .source(JobState.READY).target(JobState.DISPATCHED).event(JobEvent.DISPATCH)
                .and()
                .withExternal()
                .source(JobState.DISPATCHED).target(JobState.RUNNING).event(JobEvent.RUN)
                .and()
                .withExternal()
                .source(JobState.RUNNING).target(JobState.FINISHED).event(JobEvent.FINISH)
                .and()
                .withExternal()
                .source(JobState.READY).target(JobState.CANCEL).event(JobEvent.CANCEL)
                .and()
                .withExternal()
                .source(JobState.DISPATCHED).target(JobState.CANCEL).event(JobEvent.CANCEL)
                .and()
                .withExternal()
                .source(JobState.RUNNING).target(JobState.KILLED).event(JobEvent.KILL);
    }
}
