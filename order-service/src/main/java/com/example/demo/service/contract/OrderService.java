package com.example.demo.service.contract;

import com.example.demo.dto.CheckoutResponseDTO;
import com.example.demo.dto.OrderDTO;
import com.example.demo.dto.ProductDTO;

public interface OrderService {
    public CheckoutResponseDTO checkout(Long userId);

    public void cancelOrder(Long orderId);

    public OrderDTO getOrderDetails(Long orderId);

    public OrderDTO addItem(ProductDTO productDTO, int quantity, Long userId);

    public void initOrder(Long userId);
}
