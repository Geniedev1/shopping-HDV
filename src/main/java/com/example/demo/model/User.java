package com.example.demo.model;
import java.util.List;
import com.example.demo.exception.StatusUserIncorretException;
import com.example.demo.model.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import com.example.demo.model.UserStatus;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;

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
    private String name;
    private String email;
    private String password;
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