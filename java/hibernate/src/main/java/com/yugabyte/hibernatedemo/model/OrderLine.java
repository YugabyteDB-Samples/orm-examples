package com.yugabyte.hibernatedemo.model;


import java.io.Serializable;
import java.util.UUID;

import javax.persistence.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "orderline", schema = "ysql_hibernate")
@IdClass(OrderLine.IdClass.class)
public class OrderLine {

    @Id
    private UUID orderId;

    @Id
    @ManyToOne
    @JoinColumn(name="orderId", referencedColumnName="orderId", insertable=false, updatable=false)
    @OnDelete(action=OnDeleteAction.CASCADE)
    private Order order;

    @Id
    private Long productId;

    @Id
    @ManyToOne
    @JoinColumn(name="productId", referencedColumnName="productId", insertable=false, updatable=false)
    //@OnDelete(action=OnDeleteAction.CASCADE)
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
