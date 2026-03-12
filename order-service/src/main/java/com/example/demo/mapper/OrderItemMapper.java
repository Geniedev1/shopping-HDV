package com.example.demo.mapper;

import com.example.demo.dto.OrderItemDTO;
import com.example.demo.model.OrderItem;

public class OrderItemMapper {
  public static OrderItemDTO toDTO(OrderItem orderItem)
  {
        OrderItemDTO orderItemDTO = new OrderItemDTO();
        orderItemDTO.setId(orderItem.getId());
        orderItemDTO.setProductDTO(ProductMapper.toDTO(orderItem.getProduct()));
        orderItemDTO.setQuantity(orderItem.getQuantity());
        return orderItemDTO;
  }    
  public static OrderItem toEntity(OrderItemDTO orderItemDTO)
  {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(orderItemDTO.getId());
        orderItem.setProduct(ProductMapper.toEntity(orderItemDTO.getProductDTO()));
        orderItem.setQuantity(orderItemDTO.getQuantity());
        return orderItem;
  }
}
