package com.yugabyte.mybatis.model;


import java.io.Serializable;
import java.util.UUID;

public class OrderLine {

    private Long orderId;

    private Order order;

    private Long productId;

    private Product product;

    private Integer quantity;

    public OrderLine() {
    }

    public OrderLine(Long orderId, Long productId, Integer quantity) {
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public Long getOrderId() {
        return this.orderId;
    }

    public void setOrderId(Long orderId) {
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
