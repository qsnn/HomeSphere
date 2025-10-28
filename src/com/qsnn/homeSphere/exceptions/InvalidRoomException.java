package com.qsnn.homeSphere.exceptions;

public class InvalidRoomException extends IllegalArgumentException {
    public InvalidRoomException() {
    }
    public InvalidRoomException(String message) {
        super(message);
    }
}
