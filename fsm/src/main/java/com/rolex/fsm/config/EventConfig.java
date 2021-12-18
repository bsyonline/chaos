package com.rolex.fsm.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.statemachine.annotation.OnTransition;
import org.springframework.statemachine.annotation.WithStateMachine;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
@WithStateMachine(id = "jobMachine")
@Slf4j
public class EventConfig {

    @OnTransition(target = "START")
    public void create() {
        log.info("作业创建");
    }

    @OnTransition(source = "START", target = "READY")
    public void ready() {
        log.info("作业就绪，等待派发");
    }

    @OnTransition(source = "READY", target = "DISPATCHED")
    public void dispatch() {
        log.info("作业已派发，等待运行");
    }

    @OnTransition(source = "READY", target = "CANCEL")
    public void readyCancel(Message<JobEvent> message) {
        log.info("传递的参数：{}", message.getHeaders().get("job"));
        log.info("传递的参数：{}", message.getHeaders().get("key"));
        log.info("作业就绪，取消");
    }

    @OnTransition(source = "DISPATCH", target = "CANCEL")
    public void dispatchCancel() {
        log.info("作业已派发，取消");
    }
}
