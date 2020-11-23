package com.yugabyte.hibernatedemo.model.response;

import com.yugabyte.hibernatedemo.model.Product;

public class ResponseOrderLine {
    public Product product;
    public Integer quantity;

    public ResponseOrderLine(final Product product, final Integer quantity) {
        this.product = product;
        this.quantity = quantity;
    }
}
