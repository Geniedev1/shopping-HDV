package com.example.demo.service.impl;
import com.example.demo.model.User;
import com.example.demo.model.UserStatus;
import com.example.demo.repository.UserRepository;
import com.example.demo.model.Role;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.contract.UserService;
import com.example.demo.service.contract.FirstOrderService;
import com.example.demo.service.contract.validateentity.CheckerUser;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.demo.dto.AdminCreateUserRequest;
import com.example.demo.dto.AuthRequest;
import com.example.demo.dto.UserDTO;
import com.example.demo.exception.UserAlreadyException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import com.example.demo.exception.MailalreadySetException;
@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private CheckerUser checkerUser;
    private FirstOrderService firstOrderService;
    private PasswordEncoder paswordEncoder ;

    public UserServiceImpl(UserRepository userRepository, CheckerUser checkerUser, FirstOrderService firstOrderService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;   
        this.checkerUser = checkerUser;
        this.firstOrderService = firstOrderService;
        this.paswordEncoder = passwordEncoder;
    }
    @Override
    public void registerUser(AuthRequest authRequest) {        
        if(userRepository.existsByEmail(authRequest.getEmail())) {
            throw new UserAlreadyException("User with email: " + authRequest.getEmail() + " already exists.");
        }
        User user = UserMapper.AuthtoEntity(authRequest);
        user.setStatus(UserStatus.PENDING);
        user.setRole(Role.USER);
        user.setPassword(paswordEncoder.encode(user.getPassword()));
        User usersave = userRepository.save(user);
        firstOrderService.firstOrder(usersave);
        return;
    }   
    @Override
    public void createUserbyAdmin(AdminCreateUserRequest adminCreateUserRequest) {
        if(userRepository.existsByEmail(adminCreateUserRequest.getEmail())) {
            throw new UserAlreadyException("User with email: " + adminCreateUserRequest.getEmail() + " already exists.");
        }
        User user = UserMapper.AdminCreateUserRequesttoEntity(adminCreateUserRequest);
        userRepository.save(user);
        return;
    }
    @Override
    public List<UserDTO> getAll() {
       return userRepository.findAll().stream().map(user -> UserMapper.toDTO(user)).toList();
    }   
    @Override
    public List<UserDTO> getByRole(Role role) {
        return userRepository.findByRole(role).stream()
                .filter(user -> user.getRole() != null && user.getRole().toString().equals(role))
                .map(user -> UserMapper.toDTO(user))
                .toList();
    }   
    public void addMail(String email,Long userId) {
        User user = userRepository.findById(userId).
        orElseThrow(() -> new UserNotFoundException("User with id: " + userId + " not found."));
        if(user.getEmail()!=null)
        {
            throw new MailalreadySetException("User with email: " + email + " already exists.");
        }
    }
    @Override
    public void checkUser(Long userId) {
         if(!userRepository.existsById(userId)) {
             throw new UserNotFoundException("User with id: " + userId + " not found.");
         }  
    }
    @Override
    public void activateUser(Long userId)
    {
       checkerUser.CheckCanOrder(userId);
        User user = userRepository.findById(userId).
        orElseThrow(() -> new UserNotFoundException("User with id: " + userId + " not found."));
        user.activate();
        userRepository.save(user);
    }

    
}
