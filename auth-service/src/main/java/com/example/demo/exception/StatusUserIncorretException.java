package com.example.demo.exception;

public class StatusUserIncorretException extends RuntimeException {
    public StatusUserIncorretException(String status) {
        super("Status user is incorrect: " + status);
    }
    
}
