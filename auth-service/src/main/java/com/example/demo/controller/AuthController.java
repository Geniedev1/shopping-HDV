package com.example.demo.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.example.demo.dto.AuthRequest;
import com.example.demo.dto.AuthResponse;
import com.example.demo.security.JwtUtil;
import com.example.demo.security.UserDetailsimpl;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.service.contract.UserService;
import jakarta.validation.Valid;
import com.example.demo.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import com.example.demo.exception.UserNotAuthticationException;
import com.example.demo.mapper.UserMapper;
@RestController
@RequestMapping("api/auth")
public class AuthController {
    private UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    public AuthController(
        AuthenticationManager authenticationManager,
        JwtUtil jwtUtil,
        UserService userService
    ) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)

    public AuthResponse login(@RequestBody AuthRequest authRequest) {
     try{
           System.out.println("LOGIN API CALLED");
         Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                authRequest.getEmail(),
                authRequest.getPassword()
            )
        );
            System.out.println("LOGIN API CALLED 2");

        UserDetailsimpl userDetails = (UserDetailsimpl) authentication.getPrincipal();   
        String jwt = jwtUtil.generateToken(userDetails);
            System.out.println("LOGIN API CALLED 3");

        return UserMapper.toAuthResponse(jwt, userDetails);
     }
     catch(AuthenticationException ex){
        throw new UserNotAuthticationException("Invalid username or password");
        }
    }
@PostMapping("/register")
@ResponseStatus(HttpStatus.CREATED)
public void createUser(@Valid @RequestBody AuthRequest authRequest) {
         userService.registerUser(authRequest);
         return;
  }
    
}
//f