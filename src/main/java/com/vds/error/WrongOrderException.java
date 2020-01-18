package com.vds.error;

public class WrongOrderException extends Exception {

    public WrongOrderException() {
        super();
    }

    public WrongOrderException(String message) {
        super(message);
    }

    public WrongOrderException(String message, Throwable cause) {
        super(message, cause);
    }
}
