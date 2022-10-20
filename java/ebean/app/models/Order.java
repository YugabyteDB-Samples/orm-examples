package models;

import akka.http.scaladsl.model.DateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.ebean.Finder;
import io.ebean.Model;
import jdk.nashorn.internal.ir.annotations.Ignore;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "orders")
public class Order extends Model {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public UUID orderId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    public Date orderTime;

    @ManyToOne
    @JoinColumn(name="user_id")
    public  User orderOwner;

    @Transient
    public Long userId;

    @Column(columnDefinition = "numeric(10,2)")
    public double orderTotal;


    @Transient
    @Ignore
    public List<OrderLine> products;

    public static final Finder<UUID, Order> find = new Finder<>(Order.class);
}
