package com.yugabyte.mybatis.model.response;


import java.util.List;
import java.util.UUID;

import com.yugabyte.mybatis.model.Product;

public class ListOrdersResponse {

    public static class ResponseOrder {
        private UUID order_id;
        private Double order_total;
        private List<ResponseOrderLine> order_lines;

        public UUID getOrder_id() {
            return order_id;
        }

        public void setOrder_id(UUID order_id) {
            this.order_id = order_id;
        }

        public Double getOrder_total() {
            return order_total;
        }

        public void setOrder_total(Double order_total) {
            this.order_total = order_total;
        }

        public List<ResponseOrderLine> getOrder_lines() {
            return order_lines;
        }

        public void setOrder_lines(List<ResponseOrderLine> order_lines) {
            this.order_lines = order_lines;
        }
    }
    private String first_name;
    private String last_name;
    private String email;
    private Long user_id;

    List<ResponseOrder> orders;

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public List<ResponseOrder> getOrders() {
        return orders;
    }

    public void setOrders(List<ResponseOrder> orders) {
        this.orders = orders;
    }
}
