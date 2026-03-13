package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.OrderDTO;
import com.example.demo.dto.ProductDTO;
import com.example.demo.security.UserDetailsimpl;
import com.example.demo.service.contract.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDTO order(@RequestBody ProductDTO productDTO, @RequestParam int quantity,
            @AuthenticationPrincipal UserDetailsimpl userDetails) {
        // TODO: process POST request
        return orderService.addItem(productDTO, quantity, userDetails.getId());
    }

    @PostMapping("/checkout")
    @ResponseStatus(HttpStatus.OK)
    public String checkout(@AuthenticationPrincipal UserDetailsimpl userDetails) {
        orderService.checkout(userDetails.getId());
        return "Checkout successful";
    }

    @PostMapping("/init")
    @ResponseStatus(HttpStatus.CREATED)
    public String initOrder(@AuthenticationPrincipal UserDetailsimpl userDetails) {
        orderService.initOrder(userDetails.getId());
        return "Order initialized";
    }
}
// f