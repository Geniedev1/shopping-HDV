package com.example.demo.repository;
import com.example.demo.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import org.springframework.stereotype.Repository;
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
   
} 
