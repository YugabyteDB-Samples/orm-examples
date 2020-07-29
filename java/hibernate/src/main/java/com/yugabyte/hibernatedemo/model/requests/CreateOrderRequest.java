package com.yugabyte.hibernatedemo.model.requests;

import java.util.List;

public class CreateOrderRequest {

    public static class OrderDetails {
        private Long productId;
        private Integer units;

        public Long getProductId() {
            return productId;
        }

        public void setProductId(Long productId) {
            this.productId = productId;
        }

        public Integer getQuantity() {
            return units;
        }

        public void setQuantity(Integer quantity) {
            this.units = quantity;
        }
    }

    private Long userId;
    private List<OrderDetails> products;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<OrderDetails> getProducts() {
        return products;
    }

    public void setProducts(List<OrderDetails> products) {
        this.products = products;
    }
}
