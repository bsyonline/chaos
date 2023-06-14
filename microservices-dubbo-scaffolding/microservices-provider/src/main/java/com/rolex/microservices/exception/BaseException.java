package com.rolex.microservices.exception;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
public class BaseException extends RuntimeException {
    private final ErrorType errorType;

    public BaseException() {
        this.errorType = SystemErrorType.SYSTEM_ERROR;
    }

    public BaseException(String msg) {
        super(msg);
        this.errorType = SystemErrorType.SYSTEM_ERROR;
    }

    public BaseException(ErrorType errorType) {
        super(errorType.getMsg());
        this.errorType = errorType;
    }

    public BaseException(ErrorType errorType, String message) {
        super(message);
        this.errorType = errorType;
    }

    public BaseException(ErrorType errorType, String message, Throwable cause) {
        super(message, cause);
        this.errorType = errorType;
    }

    public ErrorType getErrorType() {
        return this.errorType;
    }
}