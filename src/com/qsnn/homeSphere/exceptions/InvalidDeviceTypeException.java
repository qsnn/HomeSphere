package com.qsnn.homeSphere.exceptions;

public class InvalidDeviceTypeException extends IllegalArgumentException {
    public InvalidDeviceTypeException() {
        super("未知的设备类型！");
    }

    public InvalidDeviceTypeException(String message) {
        super(message);
    }
}
