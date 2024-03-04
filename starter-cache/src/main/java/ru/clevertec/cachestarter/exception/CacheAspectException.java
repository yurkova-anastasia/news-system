package ru.clevertec.cachestarter.exception;

public class CacheAspectException extends RuntimeException {

    public CacheAspectException() {
        super();
    }

    public CacheAspectException(String message, Throwable cause) {
        super(message, cause);
    }

    public CacheAspectException(String message) {
        super(message);
    }
}