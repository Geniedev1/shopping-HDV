package com.example.demo.service.contract;
import java.util.List;
import com.example.demo.model.Product;
import com.example.demo.dto.ProductDTO;
public interface ProductService {
    public ProductDTO addProduct(ProductDTO productDTO);
    public List<ProductDTO> getAllProducts();
}