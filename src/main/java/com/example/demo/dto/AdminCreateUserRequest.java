package com.example.demo.dto;
import com.example.demo.model.Role;
import com.example.demo.model.UserStatus;
public class AdminCreateUserRequest {
    private String email;
    private String password;
    private Role role;
    private UserStatus status;
    public AdminCreateUserRequest() {
    }
    public AdminCreateUserRequest(String email, String password, Role role, UserStatus status) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.status = status;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public Role getRole() {
        return role;
    }
    public void setRole(Role role) {
        this.role = role;
    }
    public UserStatus getStatus() {
        return status;
    }
    public void setStatus(UserStatus status) {
        this.status = status;
    }

}
