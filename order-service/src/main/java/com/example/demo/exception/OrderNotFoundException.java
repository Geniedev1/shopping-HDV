package com.example.demo.exception;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(String orderId) {
        super("Order with ID " + orderId + " not found.");
    }   
}
