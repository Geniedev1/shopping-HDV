package com.example.demo.exception;

public class UserNotAuthticationException extends RuntimeException {
    public UserNotAuthticationException(String message) {
        super(message);
    }
    
}
