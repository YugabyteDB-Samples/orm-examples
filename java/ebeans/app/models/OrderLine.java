package models;

import io.ebean.Finder;
import io.ebean.Model;
import jdk.nashorn.internal.ir.annotations.Ignore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "orderline")
public class OrderLine extends Model {

    @EmbeddedId
    public OrderLineGroup pk;

    @Transient
    public UUID orderId;

    //@Id
    @Transient
    @ManyToOne
    @JoinColumn(name="order_id", nullable = false)
    public Order order;

    @Transient
    public Long productId;

    @Transient
    @ManyToOne
    @JoinColumn(name="product_id", nullable = false)
    public Product product;

    public Integer units;

    public OrderLine() {
    }

    public OrderLine(UUID orderId, Long productId, Integer units) {
        this.orderId = orderId;
        this.productId = productId;
        this.units = units;
    }

    public static final Finder<UUID, OrderLine> find = new Finder<>(OrderLine.class);
}

