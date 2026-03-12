package com.example.demo.dto;
public class AdminCreateUserReponse {
    private String message;
    public AdminCreateUserReponse(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }   
    public void setMessage(String message) {
        this.message = message;
    }
}