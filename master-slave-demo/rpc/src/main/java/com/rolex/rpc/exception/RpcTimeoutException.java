package com.rolex.rpc.exception;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
public class RpcTimeoutException extends RuntimeException {
    long timeoutMillis;

    public RpcTimeoutException(String message, Throwable cause, long timeoutMillis) {
        super(message, cause);
        this.timeoutMillis = timeoutMillis;
    }
}
