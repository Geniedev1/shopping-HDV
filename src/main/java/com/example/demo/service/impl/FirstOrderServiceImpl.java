package com.example.demo.service.impl;
import com.example.demo.dto.UserDTO;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.Order;
import com.example.demo.model.User;
import com.example.demo.repository.OrderRepository;
import org.springframework.stereotype.Service;
import com.example.demo.service.contract.FirstOrderService;
import com.example.demo.service.contract.validateentity.CheckerUser;
@Service
public class FirstOrderServiceImpl implements FirstOrderService {
    private OrderRepository orderRepository;
    public FirstOrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }
    @Override
    public void firstOrder(User user) {
        Order order = new Order();
        order.setUser(user);
        orderRepository.save(order);
    }    
}
