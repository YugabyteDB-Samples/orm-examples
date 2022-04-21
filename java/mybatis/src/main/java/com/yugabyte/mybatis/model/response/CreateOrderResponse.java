package com.yugabyte.mybatis.model.response;

import java.util.ArrayList;
import java.util.List;

import com.yugabyte.mybatis.model.Product;

public class CreateOrderResponse {

    private String orderId;
    private Long userId;
    private double orderTotal;

    private List<ResponseOrderLine> orderLines = new ArrayList<>();

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Long getUserId() {
        return userId;
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

    public List<ResponseOrderLine> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(List<ResponseOrderLine> orderLines) {
        this.orderLines = orderLines;
    }
}
