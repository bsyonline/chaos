package com.rolex.discovery.load;

public class CommonJobProperties {
    public static final String JOB_LAYOUT = "job.layout";
    public static final String JOB_PREFIX = "job.";
    public static final String SYS_PREFIX = "sys.";
    public static final String MACRO_DEF_PREFIX = "$";
    // 业务日期
    public static final String BIZ_DATE = "job.biz.date";
    public static final String BIZ_DATE_NON_PREFIX = "biz.date";
    // 业务时间
    public static final String BIZ_TIME = "job.biz.time";
    public static final String BIZ_TIME_NON_PREFIX = "biz.time";
    // 批次号
    public static final String BATCH_NO = "job.batch.no";
    public static final String BATCH_NO_NON_PREFIX = "batch.no";
    //运行环境
    public static final String RUN_ENV = "job.run.env";
    //返回码
    public static final String EXIT_CODE = "job.exit.code";

    // 宏定义业务时间
    public static final String DEFINE_BIZ_DATE = "${bizDate}";
    public static final String DEFINE_BIZ_DATE_1 = "${biz.date}";
    // 宏定义批次
    public static final String DEFINE_BATCH_NO = "${batchNo}";
    public static final String DEFINE_BATCH_NO_1 = "${batch.no}";

    /**
     * The type of job that will be executed. Examples: command, java, etc.
     */
    public static final String JOB_TYPE = "type";
    public static final String JOB_NAME = "job.name";
    public static final String JOB_INST_ID = "job.inst.id";
    public static final String JOB_WORKING_DIR = "job.working.dir";
    public static final String JOB_EXECUTING_DIR = "job.executing.dir";
    public static final String JOB_LOG_FILE = "job.log";
    public static final String JOB_LOG_FILE_PATH = "job.log.path";
    public static final String JOB_OUTPUT_FILE_PATH = "job.output.path";
    public static final String JOB_OUTPUT_PARAMS = "job.outputParams";
    public static final String OUTPUT_KEYS = "outPutKeys";
    public static final String JOB_OUTPUT_FILE = "job.output";
    public static final String JOB_OUTPUT_KEYS = "job.output.keys";
    public static final String JOB_COMMAND_FILE = "job.command";
    public static final String JOB_BEFORE_COMMAND_FILE = "job.before.command";
    public static final String JOB_AFTER_COMMAND_FILE = "job.after.command";
    public static final String JOB_PROPERTIES_FILE = "job.properties";
    public static final String SYS_PROPERTIES_FILE = "sys.properties";
    public static final String ENV_PROPERTIES_FILE = "env.properties";
    public static final String JOB_RESOURCE_STORAGE = "dts.resource.storage";
    public static final String JOB_RESOURCE_CLUSTER_NAMESPACE = "cluster.namespace";
    public static final String JOB_RESOURCE_CLUSTERCONF = "dts.resource.cluster.conf.dir";
    public static final String JOB_DEPEND_PACKAGES = "dts.job.packages";  // 作业中配置的原始依赖包信息
    public static final String JOB_RESOURCE_PACKAGES = "dts.resource.packages";  // 已下载的全路径依赖包
    public static final String JOB_RESOURCE_GROUP_ID = "dts.resource.group.id";  // 资源组id
    public static final String JOB_RESOURCE_GROUP = "dts.resource.group";  // 资源组名称
    public static final String JOB_RESOURCE_GROUP_NAME = "dts.resource.group.name";  // 资源组显示名称
    public static final String JOB_RESOURCE_USAGE = "dts.resource.usage";  // 资源组使用量
    public static final String JOB_RESOURCE_REQUEST_LIMIT_ENABLED = "dts.resource.requestLimitEnabled";  // 是否设置 request
    public static final String JOB_RESOURCE_REQUEST_LIMIT_PERCENT = "dts.resource.requestLimitPercent";  // request 比例
    public static final String JOB_RESOURCE_EXT_YARN_GROUP_ID = "dts.ext.yarn.resource.group.id";
    public static final String JOB_RESOURCE_EXT_YARN_GROUP_NAME = "dts.ext.yarn.resource.group.name";
    public static final String JOB_RESOURCE_EXT_YARN_GROUP_USAGE = "dts.ext.yarn.resource.usage";
    public static final String JOB_RESOURCE_EXT_YARN_GROUP = "dts.ext.yarn.resource.group";
    public static final String JOB_LOGIC_RESOURCE_GROUP = "dts.logic.resource.group";  // 逻辑资源组
    public static final String JOB_LOGIC_RESOURCE_RELATION = "dts.logic.resource.relation";  // 逻辑资源组之间关系  & , |
    public static final String JOB_PROPERTIES_K8S_AUTH_USERNAME = "k8s.auth.username";
    public static final String JOB_PROPERTIES_K8S_AUTH_PASSWORD = "k8s.auth.password";
    public static final String JOB_PROPERTIES_K8S_AUTH_REGISTRY = "k8s.auth.registry";
    public static final String JOB_PROPERTIES_K8S_AUTH_SECRET_KEY = "k8s.auth.secret-key";
    public static final String JOB_PROPERTIES_K8S_URL = "k8s.api_url";
    public static final String JOB_PROPERTIES_K8S_TOKEN = "k8s.token";
    public static final String JOB_PROPERTIES_K8S_CA = "k8s.ca.crt";
    public static final String JOB_PROPERTIES_ES_URL = "project.es.url";
    public static final String JOB_PROPERTIES_IDE_URL = "project.ide.url";
    public static final String JOB_PROPERTIES_RESOURCE_MGR_URL = "project.resource-manager.url";

    public static final String JOB_PROPERTIES_DATA_MGR_BASE_URL = "project.data-manager.base.url";
    //数据管理4个服务域名拆分
    public static final String JOB_PROPERTIES_DATA_MGR_MD_MANAGER_URL = "project.data-manager.md-manager.url";
    public static final String JOB_PROPERTIES_DATA_MGR_MD_RIGHT_MANAGER_URL = "project.data-manager.md-right-manager.url";
    public static final String JOB_PROPERTIES_DATA_MGR_MD_RIGHT_QUERY_URL = "project.data-manager.md-right-query.url";
    public static final String JOB_PROPERTIES_DATA_MGR_MD_QUERY_URL = "project.data-manager.md-query.url";

    public static final String JOB_PROPERTIES_DATA_MGR_QUALITY_ANALYSIS_URL = "project.data-manager.quality-analysis.url";
    public static final String JOB_PROPERTIES_DATA_MGR_QUALITY_RULE_CONFIGURE_URL = "project.data-manager.quality-rule-configure.url";

    public static final String JOB_PROPERTIES_DATA_MGR_URL = "project.data-manager.url";
    public static final String JOB_PROPERTIES_OPS_SLA_REPORT_URL = "project.ops-sla-report.url";
    public static final String JOB_PROPERTIES_KERBEROS_KDC_URL = "kerberos.kdc.url";
    public static final String JOB_PROPERTIES_KERBEROS_REALMS = "kerberos.realms";
    public static final String JOB_PROPERTIES_KERBEROS_ENABLE = "dts.should.proxy";
    public static final String JOB_PROPERTIES_ENABLE_MULTI_CLUSTER = "multi-cluster.enable";
    public static final String JOB_PROPERTIES_EXEC_TYPE = "dts.exec.type"; // Executor Container ExecutorGroup
    public static final String JOB_PROPERTIES_EXEC_GROUP = "ExecutorGroup"; //ExecutorGroup
    public static final String JOB_PROPERTIES_EXEC_TYPE_EXECUTOR = "Executor";
    public static final String JOB_PROPERTIES_EXEC_TYPE_CONTAINER = "Container";
    public static final String DTS_EXECUTOR_AK = "dts.executor.ak";
    public static final String DTS_EXECUTOR_SK = "dts.executor.sk";
    public static final String AUTH_SERVER_URL = "auth-server.url";
    public static final String TENANT_AK = "tenant.ak";
    public static final String TENANT_SK = "tenant.sk";
    public static final String APP_ID = "appId";
    public static final String PRIVILEGE_URL = "privilege.url";
    public static final String DEVELOP_TYPE = "develop.type"; /* 流计算的作业的作业类型  目前有web  和  jar */
    public static final String WEB = "web";
    public static final String JAR = "jar";
    public static final String DS_ID = "dsId";
    public static final String DS_TYPE = "dsType";
    public static final String CONFIGMAP_MOUNT_PATH = "configmap.mount.path";
    public static final String PVC_MOUNT_PATH = "pvc.mount.path";
    public static final String PVC_HOST_PATH = "pvc.host.path";
    public static final String JOBLOG_MOUNT_PATH = "joblog.mount.path";
    public static final String JOB_INST_FILE = "JobInstanceJson";
    public static final String CONFIGMAP_JOB_MONITOR = "JobLogAgent";
    /**
     * Total number of job retries
     */
    public static final String RETRIES_CONFIG = "job.exec.retries.times";
    public static final String IS_ONLINE = "is_online";

    /**
     * The number of retries when this job has failed.
     */
    public static final String RETRIES = "job.exec.tried.times";

    /**
     * The nested flow id path
     */
    public static final String NESTED_FLOW_PATH = "azkaban.flow.nested.path";

    /**
     * The executing job id
     */
    public static final String JOB_ID = "job.id";
    public static final String JOB_UUID = "job.uuid";

    /**
     * 当前工作流任务需要的集群配置文件路径的key
     */
    public static final String KDTS_JOB_RESOURCE_DIR = "kdts.job.resource.dir";

    public static final int DEFAULT_MAX_RUNNING_FLOW_NO = 10;
    public static final int DEFAULT_MAX_RUNNING_FLOW_NO_PER_EXECUTOR = 5;

    /**
     * 失败重跑等待时间（ms）
     */
    public static int DEFAULT_RETRY_WAIT = 5 * 60 * 1000;

    public static long NO_DEP_EVENTID = -1;

    /**
     * 作业提交人所属租户账号
     */
    public static final String TENANT_CODE = "dts.tenant.code";
    public static final String TENANT_ID = "dts.tenant.id";
    public static final String SUBMIT_USER_CODE = "dts.submit.user.code";
    public static final String SUBMIT_USER_ID = "dts.submit.user.id";
    public static final String SUBMIT_TIME = "job.submit.time";

    /**
     * 流计算实例启动key
     * id对应savePointId      flag对应type
     * flag为restart，表示重启
     */
    public static final String ENGINE = "exec.engine";
    public static final String ENGINE_VERSION = "engine.version";
    public static final String SAVE_POINT_ID = "dts.stream.start.value";
    public static final String SAVE_POINT_FLAG = "dts.stream.start.mode";
    public static final String APPEND_PARAM = "jobInstAppendParam";
    public static final String FLINK_LOG_LEVEL = "flinkJobLogLevelParams";

    public static final String RESTART = "0";

    /**
     * 开始时间和结束时间
     */
    public static String START_TIME = "pause.startTime";
    public static String END_TIME = "pause.endTime";

    /**
     * 开始时间和结束时间
     */
    public static final String PAUSE_TIME = "pause.chooseTime";
    public static final String PAUSE_BATCH_NO = "pause.batchNo";

    /**
     * 作业优先级队列最大等待时间，查询全局配置用
     */
    public static final String MAX_WAITING_TIME_TYPE = "max_wait_time";
    public static final int MAX_WAITING_TIME_CODE = 1;

    /**
     * 分片序号和分片数；
     * 作业逻辑中可以获取分片序号用于判断命中分片。
     */
    public static final String SHARDING_INDEX = "shardingIndex";
    public static final String SHARDING_TOTAL = "shardingTotal";

    public static final String YARN_ADVANCED_ENABLE = "yarn_advanced_enable";
    public static final String YARN_RESOURCE_DETAIL = "yarn_resource_detail";
    public static final String DRIVER_CU = "driver_cu";
    public static final String EXECUTOR_NUM = "executor_num";
    public static final String EXECUTOR_CU = "executor_cu";
    public static final String RESOURCE_STRATEGY = "resource_strategy";



    /**
     * JOB参数中调优参数key
     */
    public static final String JOB_OPT_PARAMS_KEY = "optParams";
    /**
     *  job调优参数名的key
     */
    public static final String JOB_OPT_PARAMS_NAME_KEY = "params";
    /**
     * job 调优参数值的key
     */
    public static final String JOB_OPT_PARAMS_VALUE_KEY = "value";
    /**
     * job调优参数类型key
     */
    public static final String JOB_OPT_PARAMS_TYPE_KEY = "type";

}