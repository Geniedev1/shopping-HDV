package com.example.demo.client;

import com.example.demo.dto.UserLookupResult;

public interface UserClient {
    UserLookupResult getUserById(Long userId);
}
