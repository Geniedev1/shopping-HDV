package com.example.demo.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.UserDTO;
import com.example.demo.service.contract.UserService;
import com.example.demo.demo.DemoModeService;
import com.example.demo.demo.DemoMode;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final DemoModeService demoModeService;

    public UserController(UserService userService, DemoModeService demoModeService) {
        this.userService = userService;
        this.demoModeService = demoModeService;
    }
@GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable Long id) {
        DemoMode mode = demoModeService.getCurrentMode();
        if (mode == DemoMode.SLOW) {
            try {
                Thread.sleep(5000); // simulate 5-second delay
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        } else if (mode == DemoMode.FAIL) {
            throw new RuntimeException("Simulated failure in auth-service from DemoMode.FAIL");
        }
        return userService.getUser(id);
    }
}
