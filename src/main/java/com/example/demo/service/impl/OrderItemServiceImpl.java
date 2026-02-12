package com.example.demo.service.impl;
import com.example.demo.dto.ProductDTO;
import com.example.demo.repository.OrderItemRepository;
import com.example.demo.service.contract.OrderItemService; 
import org.springframework.stereotype.Service;

@Service
public class OrderItemServiceImpl implements OrderItemService {
   OrderItemRepository orderItemRepository;
   public OrderItemServiceImpl(OrderItemRepository orderItemRepository) {
       this.orderItemRepository = orderItemRepository;
   }
   @Override
    public void orderItem(ProductDTO productDTO) {
       // Implementation logic here
            
    }
}
