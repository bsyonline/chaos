package com.rolex.master.manager;

import com.google.common.collect.Maps;
import com.rolex.discovery.routing.RoutingCache;
import com.rolex.master.model.Executor;
import com.rolex.master.service.DispatchCoordinator;
import com.rolex.rpc.model.proto.MsgProto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
@Slf4j
public class PickerThreadManager {

    public static final ExecutorService PICKER_THREAD_POOL = new ThreadPoolExecutor(10, 10,
            0, TimeUnit.SECONDS, new LinkedBlockingQueue<>(5), new CustomizableThreadFactory("pickerThread-"));

    public static final Map<String, Integer> PICKER_THREAD_MAP = Maps.newConcurrentMap();
    RoutingCache routingCache;
    com.rolex.master.service.ExecutorService executorService;
    DispatchCoordinator dispatchCoordinator;
    private static final int DEFAULT_MAX_EMPTY_PICK_TIMES = 10;

    public PickerThreadManager(com.rolex.master.service.ExecutorService executorService, DispatchCoordinator dispatchCoordinator) {
        this.executorService = executorService;
        this.dispatchCoordinator = dispatchCoordinator;
    }

    public PickerThreadManager(com.rolex.master.service.ExecutorService executorService, RoutingCache routingCache) {
        this.routingCache = routingCache;
        this.executorService = executorService;
    }

    /**
     * 根据连接到派发的执行机创建PickerThread
     * 如果是公共执行机，就为所有的租户创建对应的key
     * 如果是租户执行机，就为该租户创建key
     *
     * @param type
     * @param host
     * @param port
     */
    public void create(MsgProto.ExecutorType type, String host, int port) {
        log.info("create picker thread for {}", type);
        create(genKeyPath(type, host, port));
    }

    private Set<String> genKeyPath(MsgProto.ExecutorType type, String host, int port) {
        Set<String> keySet = new HashSet<>();
        Executor executor = executorService.findByIpPort(host, port);
        if (type == MsgProto.ExecutorType.system) {
            List<String> tenants = TenantManager.getTenants();
            for (String tenant : tenants) {
                keySet.add(tenant + "_" + MsgProto.ExecutorType.system.getNumber());
            }
        }
        if (type == MsgProto.ExecutorType.tenant) {
            keySet.add(executor.getTenantCode() + "_" + executor.getExecutorGroupId());
        }
        return keySet;
    }

    public void create(Set<String> keySet) {
        if (!keySet.isEmpty()) {
            for (String key : keySet) {
                if (PICKER_THREAD_MAP.get(key) == null || PICKER_THREAD_MAP.get(key).intValue() <= 0) {
                    PickerThread task = new PickerThread(key);
                    PICKER_THREAD_POOL.submit(task);
                    PICKER_THREAD_MAP.put(key, DEFAULT_MAX_EMPTY_PICK_TIMES);
                } else {
                    PICKER_THREAD_MAP.put(key, PICKER_THREAD_MAP.get(key) + 1 >= DEFAULT_MAX_EMPTY_PICK_TIMES ? DEFAULT_MAX_EMPTY_PICK_TIMES : PICKER_THREAD_MAP.get(key) + 1);
                }
            }
        }
    }

    class PickerThread implements Runnable {
        String type;
        boolean stop = false;

        public PickerThread(String type) {
            this.type = type;
        }

        @Override
        public void run() {
            while (!stop) {
                try {
                    log.info("--------------picker-{}-{}-----------", type, PICKER_THREAD_MAP.get(type) == null ? 0 : PICKER_THREAD_MAP.get(type));
                    String[] arr = type.split("_");
                    String tenantCode = arr[0];
                    long groupId = Objects.equals("system", arr[1]) ? 0 : Long.valueOf(arr[1]);
                    int count = dispatchCoordinator.dispatch(tenantCode, groupId);
                    if (count == 0) {
                        PICKER_THREAD_MAP.put(type, PICKER_THREAD_MAP.get(type) - 1);
                    }
                    if (PICKER_THREAD_MAP.get(type) <= 0) {
                        stop = true;
                        PICKER_THREAD_MAP.put(type, 0);
                        log.info("picker-{} exit", type);
                    }
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
