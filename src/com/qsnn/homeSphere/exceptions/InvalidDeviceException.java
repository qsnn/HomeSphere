package com.qsnn.homeSphere.exceptions;

public class InvalidDeviceException extends IllegalArgumentException {
    public InvalidDeviceException() {
    }

    public InvalidDeviceException(String message) {
        super(message);
    }
}
