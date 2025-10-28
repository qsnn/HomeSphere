package com.qsnn.homeSphere.exceptions;

public class InvalidUserException extends IllegalArgumentException{
    public InvalidUserException() {
        super("用户信息错误！");
    }

    public InvalidUserException(String message) {
        super(message);
    }
}
