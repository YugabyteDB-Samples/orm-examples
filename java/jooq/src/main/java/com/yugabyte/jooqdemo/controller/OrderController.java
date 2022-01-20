package com.yugabyte.jooqdemo.controller;

import com.yugabyte.jooqdemo.controller.DTO.Order;
import com.yugabyte.jooqdemo.controller.DTO.Order.Product;
import com.yugabyte.jooqdemo.controller.DTO.User;
import com.yugabyte.jooqdemo.db.tables.records.OrderLinesRecord;
import com.yugabyte.jooqdemo.exception.ResourceNotFoundException;
import org.jooq.DSLContext;
import org.jooq.Record1;
import org.jooq.Records;
import org.jooq.Select;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static com.yugabyte.jooqdemo.db.Tables.*;
import static java.util.UUID.randomUUID;
import static org.jooq.Rows.toRowArray;
import static org.jooq.impl.DSL.*;

@RestController
public class OrderController {

	private final DSLContext ctx;

	public OrderController(DSLContext ctx) {
		this.ctx = ctx;
	}

    @GetMapping("/orders")
    public List<Order> getOrders() {
		return ctx
			.select(
				ORDERS.ORDER_ID,
				ORDERS.users().USER_ID,
				row(
					ORDERS.users().USER_ID,
					ORDERS.users().FIRST_NAME,
					ORDERS.users().LAST_NAME,
					ORDERS.users().EMAIL
				).mapping(User::new),
				multiset(
					select(ORDER_LINES.PRODUCT_ID, ORDER_LINES.UNITS)
						.from(ORDER_LINES)
						.where(ORDER_LINES.ORDER_ID.eq(ORDERS.ORDER_ID))
				).convertFrom(r -> r.map(Records.mapping(Product::new))),
				ORDERS.ORDER_TOTAL
			)
			.from(ORDERS)
			.fetch(Records.mapping(Order::new));
    }

	@PostMapping("/orders")
	@Transactional
    public Order createOrder(@RequestBody Order order) {
		UUID orderId = ctx.insertInto(ORDERS)

			// In the future, let the server generate these UUIDs:
		    // https://github.com/yugabyte/yugabyte-db/issues/3472
		    .set(ORDERS.ORDER_ID, randomUUID())

			// If a user can't be found by ID, this will throw a constraint violation exception
			.set(ORDERS.USER_ID, order.userId())

			// This sums only the products that can be found. If a product cannot be found, a constraint violation
			// exception is thrown later, when actually inserting the ORDER_LINES.
		    // with deferred constraints, we could insert the ORDER_LINES first, and only then the ORDER
			.set(ORDERS.ORDER_TOTAL, orderTotal(order))
			.returning(ORDERS.ORDER_ID)
			.fetchOne(ORDERS.ORDER_ID);

		// If a product can't be found by ID, this will throw a constraint violation exception
		insertOrderLines(order, orderId);
		return new Order(orderId, order.userId(), order.user(), order.products(), order.orderTotal());
    }

	private Select<Record1<BigDecimal>> orderTotal(Order order) {
		var values = values(order.products().stream().collect(toRowArray(
			p -> val(p.productId()),
			p -> val(p.units())
		))).as(ORDER_LINES, ORDER_LINES.PRODUCT_ID, ORDER_LINES.UNITS);

		return select(sum(PRODUCTS.PRICE.times(values.field(ORDER_LINES.UNITS))))
			.from(PRODUCTS
				.join(values)
				.on(PRODUCTS.PRODUCT_ID.eq(values.field(ORDER_LINES.PRODUCT_ID))))
			.where(PRODUCTS.PRODUCT_ID.in(order.products().stream().map(Product::productId).toArray(Long[]::new)));
	}

	private void insertOrderLines(Order order, UUID orderId) {
		ctx.insertInto(ORDER_LINES)
			   .columns(ORDER_LINES.ORDER_ID, ORDER_LINES.PRODUCT_ID, ORDER_LINES.UNITS)
			   .valuesOfRecords(order.products().stream().map(p -> new OrderLinesRecord(orderId, p.productId(), p.units())).toList())
			   .execute();
	}

	@PutMapping("/orders/{orderId}")
	@Transactional
    public Order updateOrder(
		@PathVariable UUID orderId,
		@RequestBody Order order
	) {
		ctx.deleteFrom(ORDER_LINES).where(ORDER_LINES.ORDER_ID.eq(orderId)).execute();
    	insertOrderLines(order, orderId);
		ctx.update(ORDERS)
			.set(ORDERS.USER_ID, order.userId())
			.set(ORDERS.ORDER_TOTAL, orderTotal(order))
			.where(ORDERS.ORDER_ID.eq(orderId))
			.execute();

		return order;
    }

    @DeleteMapping("/orders/{orderId}")
    public ResponseEntity<?> deleteOrder(@PathVariable UUID orderId) {
		if (ctx.delete(ORDERS).where(ORDERS.ORDER_ID.eq(orderId)).execute() == 1)
			return ResponseEntity.ok().build();
		else
			throw new ResourceNotFoundException("Order not found with id " + orderId);
    }
}
