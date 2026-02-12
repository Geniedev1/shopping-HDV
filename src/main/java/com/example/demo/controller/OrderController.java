package com.example.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.demo.model.Order;
import com.example.demo.model.Role;
import com.example.demo.model.UserStatus;
import com.example.demo.service.contract.OrderService;
import com.example.demo.dto.OrderDTO;
import com.example.demo.dto.ProductDTO;
import com.example.demo.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetails;
import com.example.demo.security.UserDetailsimpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    OrderService orderService;
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }  
@PostMapping
@ResponseStatus(HttpStatus.CREATED)
public OrderDTO order(@RequestBody ProductDTO productDTO,@RequestParam int quantity,@AuthenticationPrincipal UserDetailsimpl userDetails) {
    //TODO: process POST request
   return  orderService.addItem(productDTO, quantity, userDetails.getId());
    
  }
}
//f