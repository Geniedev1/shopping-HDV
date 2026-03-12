package com.example.demo.dto;
import java.time.LocalDateTime;
import java.util.Map;

public class ApiError {

    private String code;
    private String message;
    private Map<String, String> errors;
    private LocalDateTime timestamp;

    public ApiError(String code, String message, Map<String, String> errors, LocalDateTime timestamp) {
        this.code = code;
        this.message = message;
        this.errors = errors;
        this.timestamp = timestamp;
    }

    // getters & setters
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public Map<String, String> getErrors() {
        return errors;
    }
    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
    
}
