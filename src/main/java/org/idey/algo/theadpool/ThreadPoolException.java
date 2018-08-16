package org.idey.algo.theadpool;

public class ThreadPoolException extends RuntimeException {
    public ThreadPoolException(String message) {
        super(message);
    }

    public ThreadPoolException(String message, Exception cause) {
        super(message, cause);
    }
}
