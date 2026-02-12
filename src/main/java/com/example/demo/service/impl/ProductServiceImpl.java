package com.example.demo.service.impl;
import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import com.example.demo.exception.ProductNotFoundException;
import com.example.demo.mapper.ProductMapper;
import com.example.demo.service.contract.ProductService;
import java.util.List;
import org.springframework.stereotype.Service;

import com.example.demo.dto.ProductDTO;
import java.util.ArrayList;
import com.example.demo.exception.ProductAlreadyException;
@Service
public class ProductServiceImpl implements ProductService {
    ProductRepository productRepository;
    public ProductServiceImpl(ProductRepository productRepository) {
        // Initialize with some products
     this.productRepository = productRepository;
    }

  @Override
  public ProductDTO addProduct(ProductDTO productDTO) {
     if(productRepository.existsByNameIgnoreCase(productDTO.getName())) {
         throw new ProductAlreadyException("Product with name: " + productDTO.getName() + " already exists.");
     }
    Product product = ProductMapper.toEntity(productDTO);
    productRepository.save(product);
    return ProductMapper.toDTO(product);
  }
  @Override
   public List<ProductDTO> getAllProducts() {
       return productRepository.findAll().stream()
               .map(product ->ProductMapper.toDTO(product))
               .toList();
        
   }
}
