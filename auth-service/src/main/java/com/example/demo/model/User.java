package com.example.demo.model;
import com.example.demo.exception.StatusUserIncorretException;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public  class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    private  String createdAt;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private  Role role;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private  UserStatus status;
    @Column
    private String name;
    @Column
    private String email;
    @Column
    private String password;
    @Column
    private String address;
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
    public Long getId() {
        return id;
    }   
    public void setId(Long id) {
        this.id = id;
    }   
    public String getCreatedAt() {
        return createdAt;
    }   
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
    public UserStatus getStatus() {
        return status;
    }
    public void setStatus(UserStatus status) {
        this.status = status;
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
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public User() {
    }
    public User(String name, String email, Role role, String createdAt) {
        this.name = name;
        this.email = email;
        this.status = UserStatus.PENDING;
        this.role = role;
        this.createdAt = createdAt;
    }
    public void activate() {
        if (this.status == UserStatus.ACTIVE) {
            throw new StatusUserIncorretException("User is already active.");
        }
        this.status = UserStatus.ACTIVE;
    }
    public void lock() {
        if (this.status == UserStatus.LOCKED) {
            throw new StatusUserIncorretException("User is already locked.");
        }
        this.status = UserStatus.LOCKED;
    }
}