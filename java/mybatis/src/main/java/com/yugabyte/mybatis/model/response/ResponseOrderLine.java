package com.yugabyte.mybatis.model.response;

import com.yugabyte.mybatis.model.Product;

public class ResponseOrderLine {
    public Product product;
    public Integer quantity;

    public ResponseOrderLine(final Product product, final Integer quantity) {
        this.product = product;
        this.quantity = quantity;
    }
}
