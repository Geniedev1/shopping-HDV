package com.example.demo.exception;
public class ProductAlreadyException extends RuntimeException {
    public ProductAlreadyException(String message) {
        super(message);
    }
}