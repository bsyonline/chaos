package com.rolex.fsm;

import com.rolex.fsm.config.JobEvent;
import com.rolex.fsm.config.JobState;
import com.rolex.fsm.config.JobStateMachineBuilder;
import com.rolex.fsm.model.Job;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
@Slf4j
public class Tests {
    @Autowired
    StateMachine<JobState, JobEvent> stateMachine;

    @Test
    public void test() {
        stateMachine.start();
        stateMachine.sendEvent(JobEvent.READY);
        stateMachine.sendEvent(JobEvent.CANCEL);
        log.info("状态：{}", stateMachine.getState().getId());
    }

    @Autowired
    private JobStateMachineBuilder jobStateMachineBuilder;
    @Autowired
    private BeanFactory beanFactory;

    @Test
    public void test1() throws Exception {
        StateMachine<JobState, JobEvent> stateMachine = jobStateMachineBuilder.build(beanFactory);
        stateMachine.start();
        stateMachine.sendEvent(JobEvent.READY);
        Job job = new Job(1101L, JobState.RUNNING, LocalDateTime.now());
        Message<JobEvent> message = MessageBuilder.withPayload(JobEvent.CANCEL)
                .setHeader("job", job)
                .setHeader("key", "value")
                .build();
        stateMachine.sendEvent(message);
        log.info("状态：{}", stateMachine.getState().getId());
    }

}
