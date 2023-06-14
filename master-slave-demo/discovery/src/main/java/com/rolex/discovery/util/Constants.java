package com.rolex.discovery.util;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
public class Constants {
    public static final String ROUTE_INFO_TOPIC = "dts-route-topic";
    public static final int BROADCAST_TIME_MILLIS = 10000;
    public static final int CHECK_TIME_MILLIS = 1000;
    public static final int WAIT_SERVER_TIME_MILLIS = 3000;
    /**
     * 队列Key的连接线
     */
    String PRIORITY_QUEUE_LINK = "_";
    /**
     * 租户优先级队列后缀
     */
    String PRIORITY_SUFFIX = "job_priority";
    /**
     * 租户优先级队列实体后缀
     */
    String PRIORITY_OBJ_SUFFIX = "job_priority_obj";
    /**
     * 租户已派发队列后缀
     */
    String DISPATCHED_SUFFIX = "job_dispatched";
    public static final String CPU_THRESHOLD = "cpu_threshold";
    public static final String MEM_THRESHOLD = "memory_threshold";
    /**
     * redis列表删除替换符
     */
    static String DELETE_REPLACE = "DELETE_REPLACE";
    /**
     * 租户资源池key值
     */
    String DISPATCH_TENANT_POOL = "tenant_pool";
    /**
     * 租户在执行机中的list类型的key,后面再动态拼接对应的租户ID
     */
    String EXECUTOR_TENANTID_LIST_KEY = "Executor_TenantId_List_Key_";
    /**
     * 作业类型在执行机中的list类型的key，后面再动态拼接对应的作业类型
     */
    String EXECUTOR_JOBTYPE_LIST_KEY = "Executor_JobType_List_Key_";
    /**
     * 全局配置key
     */
    String GLOBAL_CONFIG_BY_TYPE_KEY = "Global_Config_BY_TYPE_KEY_";
    /**
     * 全局作业类型统计key
     */
    String GLOBAL_COUNT_SUFFIX = "_global_job_count";
    /**
     * 全局插件并行度统计
     */
    String PLUGIN_COUNT_SUFFIX = "_plugin_left_count";
    /**
     * 全局插件并行度统计
     */
    String LOGIC_COUNT_SUFFIX = "_logic_left_count";
}
