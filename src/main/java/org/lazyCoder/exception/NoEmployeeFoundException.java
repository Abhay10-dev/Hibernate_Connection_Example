package org.lazyCoder.exception;

public class NoEmployeeFoundException extends RuntimeException {
    public NoEmployeeFoundException(String message) {
        super(message);
    }
    public NoEmployeeFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
