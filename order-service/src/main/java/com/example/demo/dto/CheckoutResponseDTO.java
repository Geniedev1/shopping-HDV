package com.example.demo.dto;

import com.example.demo.model.OrderStatus;

public class CheckoutResponseDTO {
    private String message;
    private Long orderId;
    private OrderStatus orderStatus;
    private boolean fallbackUsed;
    private String downstreamStatus;
    private String emailUsed;
    private String addressUsed;

    public CheckoutResponseDTO() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public boolean isFallbackUsed() {
        return fallbackUsed;
    }

    public void setFallbackUsed(boolean fallbackUsed) {
        this.fallbackUsed = fallbackUsed;
    }

    public String getDownstreamStatus() {
        return downstreamStatus;
    }

    public void setDownstreamStatus(String downstreamStatus) {
        this.downstreamStatus = downstreamStatus;
    }

    public String getEmailUsed() {
        return emailUsed;
    }

    public void setEmailUsed(String emailUsed) {
        this.emailUsed = emailUsed;
    }

    public String getAddressUsed() {
        return addressUsed;
    }

    public void setAddressUsed(String addressUsed) {
        this.addressUsed = addressUsed;
    }
}
