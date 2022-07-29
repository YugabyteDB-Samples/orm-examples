package models;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "products")
public class Product extends Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    public Long productId;

    public String description;

    @Column(columnDefinition = "numeric(10,2)")
    public double price;

    @Size(max = 50)
    public String productName;

    public static final Finder<Long, Product> find = new Finder<>(Product.class);
}