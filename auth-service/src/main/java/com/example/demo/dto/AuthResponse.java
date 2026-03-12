package com.example.demo.dto;
import com.example.demo.model.UserStatus;
import com.example.demo.model.Role;
public class AuthResponse {
    private String token;
    private String refreshToken;
    private String username;
    private String email;
    private Long userId;
    private Role role;
    private UserStatus userStatus;
    public AuthResponse() {
    }
    public AuthResponse(String token) {
        this.token = token;
    }
    public AuthResponse(String token, String refreshToken) {
        this.token = token;
        this.refreshToken = refreshToken;
    }
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public String getRefreshToken() {
        return refreshToken;
    }
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;   
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public Role getRole() {
        return role;
    }
    public void setRole(Role role) {
        this.role = role;
    }
    public UserStatus getUserStatus() {
        return userStatus;
    }
    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }
}
