package com.example.demo.controller;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties.Admin;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.dto.AdminCreateUserRequest;
import org.springframework.web.bind.annotation.RequestBody;
import com.example.demo.service.contract.UserService;
@RestController
@RequestMapping("api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    UserService userService;
    public AdminController(UserService userService) {
        this.userService = userService;
    }
@PostMapping("/{id}/activate")
@ResponseStatus(HttpStatus.NO_CONTENT)
public void activateUser(@PathVariable Long id) {
    userService.activateUser(id);
  }
@PostMapping("/createUser")
@ResponseStatus(HttpStatus.CREATED)
public void createUserbyAdmin(@RequestBody AdminCreateUserRequest adminCreateUserRequest) {
    userService.createUserbyAdmin(adminCreateUserRequest);
    return;
  }
}
//f