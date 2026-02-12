package com.example.demo.dto;

import com.example.demo.model.User;
import com.example.demo.model.UserStatus;
import com.example.demo.model.Role;
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private UserStatus status;
    // Getters and Setters
    private String createdAt;
    private Role role;
    public UserDTO() {
    }
    public UserDTO(Long id, String name, String email, UserStatus status, String createdAt, Role role ) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.status = status;
        this.createdAt = createdAt;
        this.role = role;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public  UserStatus getStatus() {
        return status;
    }
    public void setStatus(UserStatus status) {
        this.status = status;
    }
    public String getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
    public Role getRole() {
        return role;
    }
    public void setRole(Role role) {
        this.role = role;
    }
}