package com.example.demo.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dto.OrderDTO;
import com.example.demo.dto.ProductDTO;
import com.example.demo.exception.OrderNotFoundException;
import com.example.demo.exception.ProductNotFoundException;
import com.example.demo.mapper.OrderMapper;
import com.example.demo.model.Order;
import com.example.demo.model.OrderStatus;
import com.example.demo.model.Product;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.contract.OrderService;
import com.example.demo.client.UserClient;
import com.example.demo.dto.UserDTO;
import com.example.demo.dto.UserLookupResult;
import com.example.demo.dto.CheckoutResponseDTO;

@Service
public class OrderServiceImpl implements OrderService {
    OrderRepository orderRepository;
    ProductRepository productRepository;
    UserClient userClient;

    public OrderServiceImpl(OrderRepository orderRepository, ProductRepository productRepository,
            UserClient userClient) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.userClient = userClient;

    }

    @Override
    public void cancelOrder(Long orderId) {
        // log.debug("Cancelling order with ID: " + orderId);
        if (!orderRepository.existsById(orderId)) {
            throw new OrderNotFoundException("Order with ID: " + orderId + " not found.");
        }
        orderRepository.deleteById(orderId);
        // log.info("Order with ID: " + orderId + " has been cancelled.");
    }

    @Override
    public OrderDTO getOrderDetails(Long orderId) {
        // log.debug("Fetching details for order ID: " + orderId);
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order with ID: " + orderId + " not found."));

        return OrderMapper.toDTO(order);
        // log.info("Order with ID: " + orderId + " not found.");
    }

    @Override
    public OrderDTO addItem(ProductDTO productDTO, int quantity, Long userId) {
        List<Order> listorder = orderRepository.findByUserId(userId);
        if (listorder.isEmpty()) {
            throw new OrderNotFoundException("Order for User ID: " + userId + " not found.");
        }
        Order order = listorder.get(0);
        System.out.println(order);
        Product product = productRepository.findById(productDTO.getId()).orElseThrow(
                () -> new ProductNotFoundException("Product with ID: " + productDTO.getId() + " not found."));
        order.addItem(product, quantity);
        orderRepository.save(order);
        return OrderMapper.toDTO(order);
    }

    @Override
    public CheckoutResponseDTO checkout(Long userId) {
        List<Order> listorder = orderRepository.findByUserId(userId);
        if (listorder.isEmpty()) {
            throw new OrderNotFoundException("Order for User ID: " + userId + " not found.");
        }
        Order order = listorder.stream().filter(o -> o.getStatus() == OrderStatus.PENDING).findFirst()
                .orElseThrow(() -> new OrderNotFoundException("Order for User ID: " + userId + " not found."));

        // Get user info from User Client
        UserLookupResult lookupResult = userClient.getUserById(userId);
        UserDTO userDTO = lookupResult.getUser();
        if (userDTO != null) {
            order.setAddress(userDTO.getAddress());
            order.setEmail(userDTO.getEmail());
            System.out.println("User Address used: " + userDTO.getAddress());
            System.out.println("User Email used: " + userDTO.getEmail());
        }

        order.setStatus(OrderStatus.PAID);
        orderRepository.save(order);

        CheckoutResponseDTO response = new CheckoutResponseDTO();
        response.setMessage("Checkout Processed");
        response.setOrderId(order.getId());
        response.setOrderStatus(order.getStatus());
        response.setFallbackUsed(lookupResult.isFallbackUsed());
        response.setDownstreamStatus(lookupResult.getDownstreamStatus());
        response.setEmailUsed(order.getEmail());
        response.setAddressUsed(order.getAddress());

        return response;
    }

    @Override
    public void initOrder(Long userId) {
        List<Order> listorder = orderRepository.findByUserId(userId);
        if (listorder.isEmpty()) {
            throw new OrderNotFoundException("Order for User ID: " + userId + " not found.");
        }
        for (Order order : listorder) {
            if (order.getStatus() == OrderStatus.PENDING) {
                throw new OrderNotFoundException("Order for User ID: " + userId + " not found.");
            }
        }
        Order order = new Order();
        order.setUserId(userId);
        orderRepository.save(order);
    }
}
