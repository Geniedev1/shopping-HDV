package com.example.demo.client;

import com.example.demo.dto.UserDTO;

public interface UserClient {
    UserDTO getUserById(Long userId);
}
