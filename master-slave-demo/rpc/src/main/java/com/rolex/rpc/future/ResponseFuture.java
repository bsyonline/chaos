package com.rolex.rpc.future;

import com.rolex.rpc.model.Msg;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
@Slf4j
public class ResponseFuture {

    private final CountDownLatch latch = new CountDownLatch(1);
    private final long timeoutMillis;
    Msg response;
    private volatile boolean sendOk = true;
    private Throwable cause;

    public ResponseFuture(long timeoutMillis) {
        this.timeoutMillis = timeoutMillis;
    }

    public Msg waitResponse() throws InterruptedException {
        log.info("await and wait response");
        this.latch.await(timeoutMillis, TimeUnit.MILLISECONDS);
        return this.response;
    }

    public void setSendOk(boolean sendOk) {
        this.sendOk = sendOk;
    }

    public boolean isSendOK() {
        return sendOk;
    }

    public void setCause(Throwable cause) {
        this.cause = cause;
    }

    public Throwable getCause() {
        return cause;
    }

    public void putResponse(final Msg response) {
        this.response = response;
        this.latch.countDown();
    }
}
