package com.example.demo.exception;

public class UserAlreadyActivateException extends RuntimeException {
    public UserAlreadyActivateException(Long userId) {
        super("User with ID " + userId + " is already activated.");
    }
    
}
