package com.rolex.microservices.exception;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
public enum SystemErrorType implements ErrorType {
    SYSTEM_ERROR("-1", "出错了呢，请联系管理员"),
    SYSTEM_BUSY("000001", "系统繁忙,请稍候再试"),
    GATEWAY_NOT_FOUND_SERVICE("010404", "服务未找到"),
    GATEWAY_ERROR("010500", "网关异常"),
    GATEWAY_CONNECT_TIME_OUT("010002", "网关超时"),
    ARGUMENT_NOT_VALID("020000", "请求参数校验不通过"),
    UPLOAD_FILE_SIZE_LIMIT("020001", "上传文件大小超过限制"),
    NOT_SUPPORT_ERROR("020003", "请求类型不匹配"),
    PARAM_ERROR("000002", "业务异常"),
    DUPLICATE_PRIMARY_KEY("030000", "唯一键冲突");

    private String code;
    private String msg;

    private SystemErrorType(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMsg() {
        return this.msg;
    }
}
