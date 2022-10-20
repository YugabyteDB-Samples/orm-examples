package com.yugabyte.jooqdemo.controller;

import com.yugabyte.jooqdemo.controller.DTO.Product;
import com.yugabyte.jooqdemo.db.tables.records.ProductsRecord;
import com.yugabyte.jooqdemo.exception.ResourceNotFoundException;
import org.jooq.DSLContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.yugabyte.jooqdemo.db.Tables.PRODUCTS;
import static com.yugabyte.jooqdemo.db.Tables.USERS;
import static org.jooq.Records.mapping;

@RestController
public class ProductController {

    private final DSLContext ctx;

    public ProductController(DSLContext ctx) {
        this.ctx = ctx;
    }

    @GetMapping("/products")
    public List<Product> getProducts() {
        return ctx.selectFrom(PRODUCTS).fetch(mapping(Product::new));
    }

    @PostMapping("/products")
    public Product createProduct(@RequestBody Product product) {
        ProductsRecord record = ctx.newRecord(PRODUCTS, product);
        record.changed(PRODUCTS.PRODUCT_ID, false);
        record.insert();
        return record.into(Product.class);
    }

    @PutMapping("/products/{productId}")
    public Product updateProduct(
        @PathVariable Long productId,
        @RequestBody Product product
    ) {
        ProductsRecord record = ctx.newRecord(PRODUCTS, product);
        record.setProductId(productId);

        if (record.update() == 1)
            return product;
        else
            throw new ResourceNotFoundException("User not found with id " + productId);
    }


    @DeleteMapping("/products/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long productId) {
        if (ctx.delete(PRODUCTS).where(PRODUCTS.PRODUCT_ID.eq(productId)).execute() == 1)
            return ResponseEntity.ok().build();
        else
            throw new ResourceNotFoundException("User not found with id " + productId);
    }
}
