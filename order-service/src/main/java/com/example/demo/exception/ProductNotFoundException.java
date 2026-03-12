package com.example.demo.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String productId) {
        super("Product with ID " + productId + " not found.");
    }
}
// .\mvnw spring-boot:run
