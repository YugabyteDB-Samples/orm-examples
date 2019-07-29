package com.yugabyte.hibernatedemo.model;

import java.util.*;
import javax.persistence.*;

@Entity
@Table(name = "orders", schema = "ysql_hibernate")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID orderId;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @Transient
    private Long userId;

    @Column(columnDefinition = "numeric(10,2)")
    private double orderTotal;

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public double getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(double orderTotal) {
        this.orderTotal = orderTotal;
    }

}