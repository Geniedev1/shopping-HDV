package com.example.demo.exception;

public class UserNotHavePermissionException extends RuntimeException {
    public UserNotHavePermissionException(String message) {
        super(message);
    }
    
}
