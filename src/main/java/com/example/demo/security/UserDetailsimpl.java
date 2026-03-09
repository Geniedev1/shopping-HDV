package com.example.demo.security;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.demo.model.UserStatus;

public class UserDetailsimpl implements UserDetails {
     private static final Logger log =
            LoggerFactory.getLogger(UserDetailsimpl.class);
   private Long id;
    private String username;
    private UserStatus status;
    private Collection<? extends GrantedAuthority> authorities;
    public UserDetailsimpl(Long id, String username, UserStatus status, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.status = status;
        this.authorities = authorities;

    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
    @Override
    public String getPassword() {
        return null; //  Không lưu mật khẩu trong JWT, nên trả về null
    }
    @Override
    public String getUsername() {
        return username;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }   
    @Override
    public boolean isAccountNonLocked() {
        System.out.println("User status: " + this.status);
        log.warn("User status: " + this.status);    
        return this.status != UserStatus.LOCKED;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        System.out.println("User status for isEnabled: " + this.status);
        log.warn("User status for isEnabled: " + this.status);
        return this.status == UserStatus.ACTIVE;
    }
    public Long getId() {
        return id;
    }
    public UserStatus getStatus() {
        return status;
    }
    @Override
    public String toString() {
        return "UserDetailsimpl{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", status=" + status +
                ", authorities=" + authorities +
                '}';
    }
}
