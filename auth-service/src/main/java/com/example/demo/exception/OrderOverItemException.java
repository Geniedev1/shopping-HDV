package com.example.demo.exception;

public class OrderOverItemException extends RuntimeException {
    public OrderOverItemException(String message) {
        super(message);
    }
    
}
