package com.rolex.master.service.impl;

import com.rolex.master.model.Executor;
import com.rolex.master.service.ExecutorService;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
@Component
public class ExecutorServiceImpl implements ExecutorService {

    static Map<Integer, Executor> map = new HashMap<>();

    static {
        Executor executor = new Executor();
        executor.setExecutorGroupId(1);
        executor.setTenantCode("admin");
        map.put(0, executor);
        Executor executor1 = new Executor();
        executor1.setExecutorGroupId(2);
        executor1.setTenantCode("john");
        map.put(1, executor1);
        Executor executor2 = new Executor();
        executor2.setTenantCode("system");
        map.put(2, executor2);
    }

    @Override
    public Executor findByIpPort(String host, int port) {
        int i = new Random().nextInt(3);
        Executor executor = map.get(2);
        executor.setHost(host);
        executor.setPort(port);
        return executor;
    }
}
