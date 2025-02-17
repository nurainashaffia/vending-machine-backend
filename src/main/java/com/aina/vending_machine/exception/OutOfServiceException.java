package com.aina.vending_machine.exception;

public class OutOfServiceException extends RuntimeException {
    public OutOfServiceException(String message) {
        super(message);
    }
}
