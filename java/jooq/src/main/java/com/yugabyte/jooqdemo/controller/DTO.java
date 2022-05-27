package com.yugabyte.jooqdemo.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public final class DTO {

    public record User(long userId, String firstName, String lastName, String email) {}

    public record Product(long productId, String productName, String description, BigDecimal price) {}

    public record Order(UUID orderId, long userId, User user, List<Product> products, BigDecimal orderTotal) {
        public record Product(long productId, int units) {}
    }

    private DTO() {}
}
