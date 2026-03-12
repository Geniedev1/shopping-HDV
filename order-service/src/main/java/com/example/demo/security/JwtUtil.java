package com.example.demo.security;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    private Key getSignKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }


    //  LẤY USERNAME
    public String getUsername(String token) {
        return getClaims(token).getSubject();
    }

    public String getUsernameFromToken(String token) {
        return getUsername(token);
    }

    public Long getUserIdFromToken(String token) {
        Claims claims = getClaims(token);
        Number id = claims.get("id", Number.class);
        return id != null ? id.longValue() : null;
    }

    public String getStatusFromToken(String token) {
        Claims claims = getClaims(token);
        return claims.get("status", String.class);
    }

    public java.util.List<org.springframework.security.core.authority.SimpleGrantedAuthority> getAuthoritiesFromToken(String token) {
        Claims claims = getClaims(token);
        java.util.List<String> authorities = claims.get("authorities", java.util.List.class);
        if (authorities == null) {
            return new java.util.ArrayList<>();
        }
        return authorities.stream()
                .map(org.springframework.security.core.authority.SimpleGrantedAuthority::new)
                .collect(java.util.stream.Collectors.toList());
    }

    //  VALIDATE TOKEN
    public boolean validateToken(String token) {
        try {
            getClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
