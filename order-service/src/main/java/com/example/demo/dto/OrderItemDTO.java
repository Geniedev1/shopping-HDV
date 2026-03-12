package com.example.demo.dto;

import com.example.demo.model.Product;
import com.example.demo.dto.ProductDTO;
public class OrderItemDTO {
    private Long id;
    private ProductDTO productDTO;
    private int quantity;
    public OrderItemDTO() {
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public ProductDTO getProductDTO() {
        return productDTO;
    }
    public void setProductDTO(ProductDTO productDTO) {
        this.productDTO = productDTO;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}