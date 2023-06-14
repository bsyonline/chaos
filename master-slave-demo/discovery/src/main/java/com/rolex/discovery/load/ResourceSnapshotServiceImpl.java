package com.rolex.discovery.load;

import com.alibaba.fastjson.JSONObject;
import com.rolex.discovery.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ResourceSnapshotServiceImpl implements ResourceSnapshotService{
    /**
     * 资源快照
     */
    @Override
    public void snapShotResource(String traceId, ResourceGroupLoadInfo resourceGroupLoadInfo) {
        log.debug("{} snapShotResource params {}", traceId, JSONObject.toJSONString(resourceGroupLoadInfo));
        try {
            long snapShotTimeStamp = System.currentTimeMillis();
            resourceGroupLoadInfo.setSnapShotTimeStamp(snapShotTimeStamp);
            RedisUtil.set("snapshotResource" + resourceGroupLoadInfo.getResourceGroupId(), JSONObject.toJSONString(resourceGroupLoadInfo), 24 * 3600);
        } catch (Exception e) {
            log.error(traceId + "snapShotResource throws error", e);
        }
    }
    /**
     * 临时组装资源组负载信息
     *
     * @return
     */
    @Override
    public ResourceGroupLoadInfo tempAssembleResourceGroupLoadInfo(String traceId, Long resourceGroupId, String runEnv) {
        log.debug("{} tempAssembleResourceGroupLoadInfo params resourceGroupId {},runEnv {}", traceId, resourceGroupId, runEnv);
        return null;
    }
}