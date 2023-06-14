package com.rolex.discovery.load;

public interface ResourceSnapshotService {
    /**
     * 资源快照
     */
    void snapShotResource(String traceId, ResourceGroupLoadInfo resourceGroupLoadInfo);

    /**
     * 临时组装资源组负载信息
     *
     * @return
     */
    ResourceGroupLoadInfo tempAssembleResourceGroupLoadInfo(String traceId, Long resourceGroupId, String runEnv);
}