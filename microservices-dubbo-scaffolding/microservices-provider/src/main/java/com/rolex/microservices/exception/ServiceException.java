package com.rolex.microservices.exception;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
public class ServiceException extends BaseException {
    public ServiceException() {
    }

    public ServiceException(String msg) {
        super(msg);
    }

    public ServiceException(ErrorType errorType) {
        super(errorType);
    }

    public ServiceException(ErrorType errorType, String message) {
        super(errorType, message);
    }

    public ServiceException(ErrorType errorType, String message, Throwable cause) {
        super(errorType, message, cause);
    }
}
