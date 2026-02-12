package com.example.demo.service.contract;
import com.example.demo.model.User;
import java.util.List;

import com.example.demo.dto.AdminCreateUserReponse;
import com.example.demo.dto.AdminCreateUserRequest;
import com.example.demo.model.Role;
import com.example.demo.dto.AuthRequest;
import com.example.demo.dto.UserDTO;
public interface UserService {
   public void registerUser(AuthRequest authRequest);
   public void createUserbyAdmin(AdminCreateUserRequest adminCreateUserRequest);
   public List<UserDTO> getAll(); 
   public List<UserDTO> getByRole(Role role);
   public void checkUser(Long userId);
   public void activateUser(Long userId);
}