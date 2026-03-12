package com.example.demo.model;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.exception.OrderOverItemException;
import com.example.demo.model.OrderStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id", nullable = false)
    private Long userId;
    @OneToMany(
        mappedBy = "order",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private List<OrderItem> orderItems = new ArrayList<>();
    public List<OrderItem> getOrderItems() {
        return orderItems;
    }   
    @Column
    private String address;
    @Column
    private String email;
    @Column
    private OrderStatus status;
    public void addOrderItems(OrderItem orderItem) {
        this.orderItems.add(orderItem);
    }
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public OrderStatus getStatus() {
        return status;
    }
    public void setStatus(OrderStatus status) {
        this.status = status;
    }
    public Order() {
        this.status = OrderStatus.PENDING; 
    }   
    public Order(Long id, Long userId, List<OrderItem> orderItems) {
        this.userId = userId;
        this.orderItems = orderItems;
        this.id = id;
    }
     public void removeOrderItems(OrderItem orderItem) {
        this.orderItems.remove(orderItem);
  }
  public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
  }
  public void addItem(Product product,int quantity) {
       int len = this.orderItems.size();
       if(len + quantity > 100 ){
            throw new OrderOverItemException("Cannot add more than 100 items to the order.");
          }
    OrderItem orderItem = new OrderItem(this, product, quantity);
    this.orderItems.add(orderItem);
 }
 @Override
    public String toString()
 {
     return this.address +" " + this.status +" " + this.userId + " " + this.id;
 }
}