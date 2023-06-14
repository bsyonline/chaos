package com.rolex.discovery.load;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class ResourceGroupLoadInfo implements Serializable {
    /**
     * 资源组ID
     */
    private Long resourceGroupId;
    /**
     * 测试资源负载信息
     */
    private LoadInfo test;

    /**
     * 生产资源负载信息
     */
    private LoadInfo prod;
    /**
     * 资源快照时间
     */
    private Long snapShotTimeStamp;

    public LoadInfo getByEnv(String env) {
        switch (env) {
            case "test":
                return test;
            case "prod":
            case "online":
                return prod;
            default:
                return null;
        }
    }

    @Getter
    @Setter
    @ToString
    public static class LoadInfo implements Serializable {
        /**
         * 单位为核数
         */
        private double TotalCPU;
        /**
         * 单位为M
         */
        private double TotalMEM;
        private double AllowanceCPU;
        private double AllowanceMEM;
    }
}
