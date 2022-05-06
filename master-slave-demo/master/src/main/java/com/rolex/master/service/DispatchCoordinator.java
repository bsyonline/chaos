package com.rolex.master.service;

import com.google.common.collect.Lists;
import com.rolex.master.manager.ExecutorManager;
import com.rolex.master.model.JobInstance;
import com.rolex.master.model.JobPriorityInfo;
import com.rolex.rpc.model.proto.MsgProto;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
@Component
@Slf4j
public class DispatchCoordinator {

    int batch = 1;
    @Autowired
    ExecutorManager executorManager;

    public int dispatch(String tenantCode, long groupId) throws InterruptedException {
        int count = 0;
        List<JobInstance> instanceList = pickup(tenantCode, groupId, 30000, batch);
        if (instanceList != null && !instanceList.isEmpty()) {
            count = instanceList.size();
            log.debug("pickup jobInsts, {}", instanceList);

            List<JobPriorityInfo> allowDispatchJobs = instanceList.stream().map(inst -> assembleJobPriorityInfo(inst)).collect(Collectors.toList());
            send(allowDispatchJobs);
        }
        return count;
    }

    List<JobInstance> pickup(String tenantCode, long groupId, long awaitTime, int batch) {
        List<JobInstance> list = Lists.newArrayList();

        return list;
    }

    private JobPriorityInfo assembleJobPriorityInfo(JobInstance jobInst) {
        JobPriorityInfo jobPriorityInfo = new JobPriorityInfo();
        jobPriorityInfo.setJobInstId(jobInst.getId());
        return jobPriorityInfo;
    }

    private void send(List<JobPriorityInfo> jobInstanceList) throws InterruptedException {
        for (JobPriorityInfo jobInstance : jobInstanceList) {
            MsgProto msg = MsgProto.newBuilder()
                    .setType(MsgProto.CommandType.JOB_REQUEST)
                    .setJobId(jobInstance.getJobInstId())
                    .build();
            Channel channel = executorManager.select();
            if (channel != null) {
                //发送到执行机
                ChannelFuture channelFuture = sendMsg(channel, msg);
                channelFuture.addListener((ChannelFutureListener) channelFuture1 -> {
                    if (channelFuture1.isSuccess()) {
                        ChannelFuture await = channelFuture1.await();
                        Void unused = await.get();
                        log.info("成功了,{}", unused);
                    } else {
                        log.info("失败了,{}", channelFuture1.get());
                    }
                });
                log.info("send successful, {}", msg);
            } else {
                String key = jobInstance.getTenantCode() + "_" + jobInstance.getExecutorGroupId();
                log.warn("executor not found, {}", key);
            }
        }
    }

    private ChannelFuture sendMsg(Channel channel, MsgProto msg) throws InterruptedException {
        return channel.writeAndFlush(msg).sync();
    }
}
