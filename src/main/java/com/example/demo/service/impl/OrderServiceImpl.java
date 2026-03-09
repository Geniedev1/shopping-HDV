package com.example.demo.service.impl;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dto.OrderDTO;
import com.example.demo.dto.ProductDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.exception.OrderNotFoundException;
import com.example.demo.exception.ProductNotFoundException;
import com.example.demo.mapper.OrderMapper;
import com.example.demo.model.Order;
import com.example.demo.model.Product;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.contract.OrderService;
@Service
public class OrderServiceImpl implements OrderService {
    OrderRepository orderRepository;
    ProductRepository productRepository;
  public OrderServiceImpl(OrderRepository orderRepository,ProductRepository productRepository) {
      this.orderRepository = orderRepository;  
        this.productRepository = productRepository;

  }
    @Override
  public OrderDTO placeOrder(OrderDTO orderDTO , UserDTO userDTO) {
    //   log.debug("Placing order for user: " + userId + " with products: " + productQuantities);
        
        Order order = OrderMapper.toEntity(orderDTO);
        order.setUserId(userDTO.getId());
        orderRepository.save(order);    
        return OrderMapper.toDTO(order);
  } 
  @Override
  public void cancelOrder(Long orderId) {
//   log.debug("Cancelling order with ID: " + orderId);
    if(!orderRepository.existsById(orderId)){
        throw new OrderNotFoundException("Order with ID: " + orderId + " not found.");
    } 
    orderRepository.deleteById(orderId);
//    log.info("Order with ID: " + orderId + " has been cancelled.");
  }
     @Override 
    public OrderDTO getOrderDetails(Long orderId) {
    //    log.debug("Fetching details for order ID: " + orderId);
          Order order = orderRepository.findById(orderId).
          orElseThrow(() -> new OrderNotFoundException("Order with ID: " + orderId + " not found."));
          
          return OrderMapper.toDTO(order);
        // log.info("Order with ID: " + orderId + " not found.");
    }
    @Override
    public OrderDTO addItem(ProductDTO productDTO,int quantity,Long userId) {
        List<Order> listorder = orderRepository.findByUserId(userId);
        if(listorder.isEmpty()) {
            throw new OrderNotFoundException("Order for User ID: " + userId + " not found.");
        }
        Order order = listorder.get(0);
        Product product = productRepository.findById(productDTO.getId()).
        orElseThrow(() -> new ProductNotFoundException("Product with ID: " + productDTO.getId() + " not found."));
        order.addItem(product, quantity);
        orderRepository.save(order);
        return OrderMapper.toDTO(order);
    }

}
