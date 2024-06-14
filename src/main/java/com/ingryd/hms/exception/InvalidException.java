package com.ingryd.hms.exception;

/**
 * Wraps all invalid scenarios.
 */
public class InvalidException extends Exception{
    public InvalidException(String message) {
        super(message);
    }
}
