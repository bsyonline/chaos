package com.rolex.event.manager;

import com.google.gson.Gson;
import com.rolex.event.manager.exchange.EventExchange;
import com.rolex.model.PipelineContext;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author rolex
 * @since 2023/7/1
 */
@SpringBootApplication
public class App implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//        PipelineContext pipelineContext = new PipelineContext();
//        Event innerEvent = new Event();
//        innerEvent.setEventName("innerEvent");
//        innerEvent.setEventType(EventType.innerEvent);
//        innerEvent.setEventId("123");
//        pipelineContext.setEvent(innerEvent);
//        pipelineContext.setTraceId(UUID.randomUUID().toString());
        EventExchange eventExchange = new EventExchange();
//        eventExchange.exchange(pipelineContext);
//        System.out.println("--");
//        PipelineContext pipelineContext1 = new PipelineContext();
//        Event outEvent = new Event();
//        outEvent.setEventName("outerFlowEvent");
//        outEvent.setEventType(EventType.outerFlowEvent);
//        outEvent.setEventId("456");
//        pipelineContext1.setEvent(outEvent);
//        pipelineContext1.setTraceId(UUID.randomUUID().toString());
//        eventExchange.exchange(pipelineContext1);

        System.out.println("--");
        String s = "{\"traceId\":\"faab48cf-f22c-4c7c-87e4-fa15f2a230d2\",\"event\":{\"eventName\":\"outerFlowEvent\",\"eventId\":\"456\",\"eventType\":\"outerFlowEvent\",\"status\":0},\"flowInstance\":{\"id\":111,\"flowName\":\"aaa\",\"status\":20},\"footprint\":{\"RegistryHandler\":true,\"FlowInstantiationHandler\":true}}";
        PipelineContext pipelineContext2 = new Gson().fromJson(s, PipelineContext.class);
        pipelineContext2.setCompensate(true);
        eventExchange.exchange(pipelineContext2);

    }
}
