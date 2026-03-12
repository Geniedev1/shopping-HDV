package com.example.demo.mapper;

import com.example.demo.dto.ProductDTO;
import com.example.demo.model.Product;

public class ProductMapper {
     public static Product toEntity(ProductDTO productDTO) {
        Product product = new Product();
        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        return product;
    }  
    public static Product toEntitywithId(ProductDTO productDTO) {
        Product product = new Product();
        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        return product;
    }
    public static ProductDTO toDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setPrice(product.getPrice());
        return productDTO;
    }
}
