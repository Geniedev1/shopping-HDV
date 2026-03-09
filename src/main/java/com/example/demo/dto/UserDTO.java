package com.example.demo.dto;
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private String status;
    private String createdAt;
    private String role;
    private String address;
    public UserDTO() {
    }
    public UserDTO(Long id, String name, String email, String status, String createdAt, String role, String address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.status = status;
        this.createdAt = createdAt;
        this.role = role;
        this.address = address;
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
    public  String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
 
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
}