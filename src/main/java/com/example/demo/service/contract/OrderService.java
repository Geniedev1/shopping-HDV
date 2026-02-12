package com.example.demo.service.contract;
import java.util.List;
import java.util.Map;
import com.example.demo.dto.UserDTO;
import com.example.demo.dto.OrderDTO;
import com.example.demo.model.Order;
import com.example.demo.dto.OrderItemDTO;
import com.example.demo.dto.ProductDTO;
public interface OrderService {
 public OrderDTO placeOrder(OrderDTO orderRequest, UserDTO userDTO);
 public void cancelOrder(Long orderId);
 public OrderDTO getOrderDetails(Long orderId);
 public OrderDTO addItem(ProductDTO productDTO, int quantity, Long userId);
} 
