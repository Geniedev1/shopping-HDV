package com.example.demo.mapper;

import com.example.demo.dto.OrderDTO;
import com.example.demo.dto.OrderItemDTO;
import com.example.demo.model.Order;
import com.example.demo.model.OrderItem;

public class OrderMapper {
    public static Order toEntity(OrderDTO orderDTO) {
        Order order = new Order();
        order.setId(orderDTO.getId());
        for(OrderItemDTO orderItemDTO : orderDTO.getOrderItemsDTO()) {
            order.addOrderItems(OrderItemMapper.toEntity(orderItemDTO));
        }
        return order;
    }
     public static OrderDTO toDTO(Order order) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(order.getId());
        for(OrderItem orderItem : order.getOrderItems()) {
            orderDTO.getOrderItemsDTO().add(OrderItemMapper.toDTO(orderItem));
        }
        return orderDTO;
    }
}
