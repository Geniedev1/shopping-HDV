package com.example.demo.dto;
import java.util.Map;
import com.example.demo.model.User;
import com.example.demo.model.OrderItem;
import com.example.demo.model.Product;
import java.util.List;
public class OrderDTO {
     private Long id;
     private List<OrderItemDTO> orderItemsDTO = new java.util.ArrayList<>();
        
     public OrderDTO (){
     }    
        public Long getId() {
            return id;
        }
        public void setId(Long id) {
            this.id = id;
        }
        public List<OrderItemDTO> getOrderItemsDTO() {
            return orderItemsDTO;
        }
        public void setOrderItems(List<OrderItemDTO> orderItemsDTO) {
            this.orderItemsDTO = orderItemsDTO;
        }
        public void addOrderItemsDTO(OrderItemDTO orderItemDTO) {
            this.orderItemsDTO.add(orderItemDTO);
        }
        public void removeOrderItemsDTO(OrderItemDTO orderItemDTO) {
            this.orderItemsDTO.remove(orderItemDTO);
        }
        public void clearOrderItemsDTO() {
            this.orderItemsDTO.clear();
        }
        
        
}
