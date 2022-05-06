package com.rolex.master.processor;

import com.rolex.master.manager.PickerThreadManager;
import com.rolex.master.service.DispatchCoordinator;
import com.rolex.master.service.ExecutorService;
import com.rolex.rpc.model.MsgBody;
import com.rolex.rpc.model.proto.MsgProto;
import com.rolex.rpc.processor.NettyProcessor;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
@Slf4j
public class RegistryProcessor implements NettyProcessor {

    ExecutorService executorService;
    DispatchCoordinator dispatchCoordinator;

    public RegistryProcessor(ExecutorService executorService, DispatchCoordinator dispatchCoordinator) {
        this.executorService = executorService;
        this.dispatchCoordinator = dispatchCoordinator;
    }

    @Override
    public void process(Channel channel, MsgBody msg) throws InterruptedException {

    }

    @Override
    public void process4proto(Channel channel, MsgProto msg) throws InterruptedException {
        log.info("registry executor info: {}", msg);
        new PickerThreadManager(executorService, dispatchCoordinator).create(msg.getExecutorType(), msg.getHost(), msg.getPort());
    }


}
