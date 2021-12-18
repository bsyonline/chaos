package com.rolex.fsm.config;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.stereotype.Component;

import java.util.EnumSet;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
@Component
public class JobStateMachineBuilder {
    private final static String MACHINE_ID = "jobMachine";

    public StateMachine<JobState, JobEvent> build(BeanFactory beanFactory) throws Exception {
        StateMachineBuilder.Builder<JobState, JobEvent> builder = StateMachineBuilder.builder();

        System.out.println("构建Job状态机");

        builder.configureConfiguration()
                .withConfiguration()
                .machineId(MACHINE_ID)
                .beanFactory(beanFactory);

        builder.configureStates()
                .withStates().initial(JobState.START)
                .states(EnumSet.allOf(JobState.class));

        builder.configureTransitions()
                .withExternal()
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

        return builder.build();
    }

    @Bean
    public Action<JobState, JobEvent> action() {
        return context -> System.out.println(context);
    }
}
