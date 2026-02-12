package com.example.demo.exception;

import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.demo.dto.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.HttpStatus;
import com.example.demo.exception.UserNotFoundException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import com.example.demo.dto.ApiError;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.ResponseEntity;
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handlerUserNotFound(UserNotFoundException ex) {
        return new ErrorResponse(
            ex.getMessage(),
            "USER_NOT_FOUND",
            LocalDateTime.now()
        );
    }
    @ExceptionHandler(StatusUserIncorretException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleStatusUserIncorret(StatusUserIncorretException ex) {
        return new ErrorResponse(
            ex.getMessage(),
            "STATUS_USER_INCORRECT",
            LocalDateTime.now()
        );
    }
    @ExceptionHandler(OrderNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleOrderNotFound(OrderNotFoundException ex) {
        return new ErrorResponse(
            ex.getMessage(),
            "ORDER_NOT_FOUND",
            LocalDateTime.now()
        );
    }
    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleProductNotFound(ProductNotFoundException ex) {
        return new ErrorResponse(
            ex.getMessage(),
            "PRODUCT_NOT_FOUND",
            LocalDateTime.now()
        );
    }
    @ExceptionHandler(UserAlreadyActivateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleUserAlreadyActivate(UserAlreadyActivateException ex) {
        return new ErrorResponse(
            ex.getMessage(),
            "USER_ALREADY_ACTIVATED",
            LocalDateTime.now()
        );
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });
          ex.getBindingResult()
      .getFieldErrors()
      .forEach(error ->
          errors.put(error.getField(), error.getDefaultMessage())
      );
  ApiError apiError = new ApiError(
      "VALIDATION_ERROR",
      "Invalid request data",
      errors,
      LocalDateTime.now()
  );
  return ResponseEntity.badRequest().body(
        apiError
    );    }
    @ExceptionHandler(ProductAlreadyException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleProductAlreadyException(ProductAlreadyException ex) {
        return new ErrorResponse(
            ex.getMessage(),
            "PRODUCT_ALREADY_EXISTS",
            LocalDateTime.now()
        );
    }
    @ExceptionHandler(UserAlreadyException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleUserAlreadyException(UserAlreadyException ex) {
        return new ErrorResponse(
            ex.getMessage(),
            "USER_ALREADY_EXISTS",
            LocalDateTime.now()
        );
    }
    @ExceptionHandler(MailalreadySetException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMailalreadySetException(MailalreadySetException ex) {
        return new ErrorResponse(
            ex.getMessage(),
            "MAIL_ALREADY_SET",
            LocalDateTime.now()
        );
    }
    @ExceptionHandler(OrderOverItemException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleOrderOverItemException(OrderOverItemException ex) {
        return new ErrorResponse(
            ex.getMessage(),
            "ORDER_OVER_ITEM_LIMIT",
            LocalDateTime.now()
        );
    }
    @ExceptionHandler(UserNotAuthticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse handleUserNotAuthticationException(UserNotAuthticationException ex) {
        return new ErrorResponse(
            ex.getMessage(),
            "USER_NOT_AUTHENTICATED",
            LocalDateTime.now()
        );
    }
    @ExceptionHandler(UserNotHavePermissionException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleUserNotHavePermissionException(UserNotHavePermissionException ex) {
        return new ErrorResponse(
            ex.getMessage(),
            "USER_NOT_HAVE_PERMISSION",
            LocalDateTime.now()
        );
    };
}