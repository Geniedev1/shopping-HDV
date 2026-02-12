package com.example.demo.security;
import java.io.IOException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.security.core.AuthenticationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class CustomAuthFailureHandler
        implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception)
            throws IOException {

        String message = "Đăng nhập thất bại";

        Throwable cause = exception.getCause();

        if (cause instanceof DisabledException) {
            message = "Tài khoản chưa kích hoạt";
        } else if (cause instanceof LockedException) {
            message = "Tài khoản bị khóa";
        } else if (cause instanceof BadCredentialsException) {
            message = "Email hoặc mật khẩu không đúng";
        }

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");
        response.getWriter().write(
            "{\"message\":\"" + message + "\"}"
        );
    }
}
