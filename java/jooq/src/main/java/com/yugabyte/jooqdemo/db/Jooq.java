/*
 * This file is generated by jOOQ.
 */
package com.yugabyte.jooqdemo.db;


import com.yugabyte.jooqdemo.db.tables.OrderLines;
import com.yugabyte.jooqdemo.db.tables.Orders;
import com.yugabyte.jooqdemo.db.tables.Products;
import com.yugabyte.jooqdemo.db.tables.Users;

import java.util.Arrays;
import java.util.List;

import javax.annotation.processing.Generated;

import org.jooq.Catalog;
import org.jooq.Table;
import org.jooq.impl.SchemaImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "https://www.jooq.org",
        "jOOQ version:3.16.2",
        "schema version:jooq_1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Jooq extends SchemaImpl {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>jooq</code>
     */
    public static final Jooq JOOQ = new Jooq();

    /**
     * The table <code>jooq.order_lines</code>.
     */
    public final OrderLines ORDER_LINES = OrderLines.ORDER_LINES;

    /**
     * The table <code>jooq.orders</code>.
     */
    public final Orders ORDERS = Orders.ORDERS;

    /**
     * The table <code>jooq.products</code>.
     */
    public final Products PRODUCTS = Products.PRODUCTS;

    /**
     * The table <code>jooq.users</code>.
     */
    public final Users USERS = Users.USERS;

    /**
     * No further instances allowed
     */
    private Jooq() {
        super("jooq", null);
    }


    @Override
    public Catalog getCatalog() {
        return DefaultCatalog.DEFAULT_CATALOG;
    }

    @Override
    public final List<Table<?>> getTables() {
        return Arrays.asList(
            OrderLines.ORDER_LINES,
            Orders.ORDERS,
            Products.PRODUCTS,
            Users.USERS
        );
    }
}
