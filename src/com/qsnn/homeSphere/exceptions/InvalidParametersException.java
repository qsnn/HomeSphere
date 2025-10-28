package com.qsnn.homeSphere.exceptions;

public class InvalidParametersException extends IllegalArgumentException {
    public InvalidParametersException() {
    }

    public InvalidParametersException(String message) {
        super(message);
    }
}
