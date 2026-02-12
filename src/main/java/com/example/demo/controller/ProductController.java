package com.example.demo.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.contract.ProductService;
import com.example.demo.model.Product;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.dto.ProductDTO;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.ResponseStatus;
@RestController
@RequestMapping("/api/products")
public class ProductController {
    ProductService productService;
    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public ProductDTO addProduct(@RequestBody ProductDTO productDTO) { 
        return productService.addProduct(productDTO);
 }
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<ProductDTO> getAllProduct() {
        return productService.getAllProducts();
    }
    
}
//f