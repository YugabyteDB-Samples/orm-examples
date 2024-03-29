/*
 * This file is generated by jOOQ.
 */
package com.yugabyte.jooqdemo.db.tables.records;


import com.yugabyte.jooqdemo.db.tables.OrderLines;

import java.util.UUID;

import javax.annotation.processing.Generated;

import org.jooq.Field;
import org.jooq.Record2;
import org.jooq.Record3;
import org.jooq.Row3;
import org.jooq.impl.UpdatableRecordImpl;


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
public class OrderLinesRecord extends UpdatableRecordImpl<OrderLinesRecord> implements Record3<UUID, Long, Integer> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>jooq.order_lines.order_id</code>.
     */
    public void setOrderId(UUID value) {
        set(0, value);
    }

    /**
     * Getter for <code>jooq.order_lines.order_id</code>.
     */
    public UUID getOrderId() {
        return (UUID) get(0);
    }

    /**
     * Setter for <code>jooq.order_lines.product_id</code>.
     */
    public void setProductId(Long value) {
        set(1, value);
    }

    /**
     * Getter for <code>jooq.order_lines.product_id</code>.
     */
    public Long getProductId() {
        return (Long) get(1);
    }

    /**
     * Setter for <code>jooq.order_lines.units</code>.
     */
    public void setUnits(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>jooq.order_lines.units</code>.
     */
    public Integer getUnits() {
        return (Integer) get(2);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record2<UUID, Long> key() {
        return (Record2) super.key();
    }

    // -------------------------------------------------------------------------
    // Record3 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row3<UUID, Long, Integer> fieldsRow() {
        return (Row3) super.fieldsRow();
    }

    @Override
    public Row3<UUID, Long, Integer> valuesRow() {
        return (Row3) super.valuesRow();
    }

    @Override
    public Field<UUID> field1() {
        return OrderLines.ORDER_LINES.ORDER_ID;
    }

    @Override
    public Field<Long> field2() {
        return OrderLines.ORDER_LINES.PRODUCT_ID;
    }

    @Override
    public Field<Integer> field3() {
        return OrderLines.ORDER_LINES.UNITS;
    }

    @Override
    public UUID component1() {
        return getOrderId();
    }

    @Override
    public Long component2() {
        return getProductId();
    }

    @Override
    public Integer component3() {
        return getUnits();
    }

    @Override
    public UUID value1() {
        return getOrderId();
    }

    @Override
    public Long value2() {
        return getProductId();
    }

    @Override
    public Integer value3() {
        return getUnits();
    }

    @Override
    public OrderLinesRecord value1(UUID value) {
        setOrderId(value);
        return this;
    }

    @Override
    public OrderLinesRecord value2(Long value) {
        setProductId(value);
        return this;
    }

    @Override
    public OrderLinesRecord value3(Integer value) {
        setUnits(value);
        return this;
    }

    @Override
    public OrderLinesRecord values(UUID value1, Long value2, Integer value3) {
        value1(value1);
        value2(value2);
        value3(value3);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached OrderLinesRecord
     */
    public OrderLinesRecord() {
        super(OrderLines.ORDER_LINES);
    }

    /**
     * Create a detached, initialised OrderLinesRecord
     */
    public OrderLinesRecord(UUID orderId, Long productId, Integer units) {
        super(OrderLines.ORDER_LINES);

        setOrderId(orderId);
        setProductId(productId);
        setUnits(units);
    }
}
