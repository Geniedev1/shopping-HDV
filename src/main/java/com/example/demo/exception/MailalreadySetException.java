package com.example.demo.exception;
public class MailalreadySetException extends RuntimeException {
    public MailalreadySetException(String message) {
        super(message);
    }
}
