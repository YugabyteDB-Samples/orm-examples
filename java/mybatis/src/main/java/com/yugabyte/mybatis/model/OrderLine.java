package com.yugabyte.mybatis.model;


import java.io.Serializable;
import java.util.UUID;

public class OrderLine {

    private UUID orderId;

    private Order order;

    private Long productId;

    private Product product;

    private Integer quantity;

    public OrderLine() {
    }

    public OrderLine(UUID orderId, Long productId, Integer quantity) {
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public UUID getOrderId() {
        return this.orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    static class IdClass implements Serializable {
        private UUID orderId;
        private Long productId;
    }
}
