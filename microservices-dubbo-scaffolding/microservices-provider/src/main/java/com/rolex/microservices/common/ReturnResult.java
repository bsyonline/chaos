package com.rolex.microservices.common;

import com.rolex.microservices.exception.BaseException;
import com.rolex.microservices.exception.ErrorType;
import com.rolex.microservices.exception.SystemErrorType;
import net.minidev.json.annotate.JsonIgnore;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
public class ReturnResult<T> implements Serializable {
    public static final String SUCCESSFUL_CODE = "000000";
    public static final String SUCCESSFUL_MESG = "操作成功";
    private String code;
    private String msg;
    private LocalDateTime timestamp;
    private T data;

    public ReturnResult() {
        this.timestamp = LocalDateTime.now();
    }

    public ReturnResult(ErrorType errorType) {
        this.code = errorType.getCode();
        this.msg = errorType.getMsg();
        this.timestamp = LocalDateTime.now();
    }

    private ReturnResult(ErrorType errorType, String msg) {
        this.code = errorType.getCode();
        this.msg = msg;
        this.timestamp = LocalDateTime.now();
    }

    public ReturnResult(ErrorType errorType, T data) {
        this(errorType);
        this.data = data;
    }

    private ReturnResult(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.timestamp = LocalDateTime.now();
    }

    public static ReturnResult success(Object data) {
        return new ReturnResult("000000", "操作成功", data);
    }

    public static ReturnResult success() {
        return success((Object)null);
    }

    public static ReturnResult fail() {
        return new ReturnResult(SystemErrorType.SYSTEM_ERROR);
    }

    public static ReturnResult fail(BaseException baseException) {
        return fail((BaseException)baseException, (Object)null);
    }

    public static ReturnResult fail(BaseException baseException, Object data) {
        return new ReturnResult(baseException.getErrorType(), data);
    }

    public static ReturnResult fail(ErrorType errorType, String msg) {
        return new ReturnResult(errorType, msg);
    }

    public static ReturnResult fail(ErrorType errorType, Object data) {
        return new ReturnResult(errorType, data);
    }

    public static ReturnResult fail(ErrorType errorType) {
        return fail(errorType, errorType.getMsg());
    }

    public static ReturnResult fail(Object data) {
        return new ReturnResult(SystemErrorType.SYSTEM_ERROR, data);
    }

    @JsonIgnore
    public boolean isSuccess() {
        return "000000".equals(this.code);
    }

    @JsonIgnore
    public boolean isFail() {
        return !this.isSuccess();
    }

    public String getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }

    public LocalDateTime getTimestamp() {
        return this.timestamp;
    }

    public T getData() {
        return this.data;
    }
}
