package com.example.demo.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    public JwtAuthenticationFilter( JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }
    @Override
    protected void doFilterInternal(
                                     HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws java.io.IOException, ServletException {
             String authHeader = request.getHeader("Authorization");
             if(authHeader == null || !authHeader.startsWith("Bearer ")) {
               filterChain.doFilter(request, response);
                return;             
             }
            String token = authHeader.substring(7);
            if(!jwtUtil.validateToken(token)) {
                filterChain.doFilter(request, response);
                return;
            }
            Long id = jwtUtil.getUserIdFromToken(token);
            String username = jwtUtil.getUsernameFromToken(token);
            var authorities = jwtUtil.getAuthoritiesFromToken(token);
            UserDetails userDetails = new UserDetailsimpl(id, username, authorities);
            System.out.println("Authenticated user: " + userDetails);
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
           SecurityContextHolder.getContext().setAuthentication(authentication);
          filterChain.doFilter(request, response);
    }
}
