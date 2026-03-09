package com.example.demo.security;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDetailsimpl implements UserDetails {
     private static final Logger log =
            LoggerFactory.getLogger(UserDetailsimpl.class);
   private Long id;
    private String username;
    private Collection<? extends GrantedAuthority> authorities;
    public UserDetailsimpl(Long id, String username, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
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
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }
    public Long getId() {
        return id;
    }
   
    @Override
    public String toString() {
        return "UserDetailsimpl{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", authorities=" + authorities +
                '}';
    }
}
