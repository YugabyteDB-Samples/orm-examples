package com.yugabyte.springdemo.model;

import java.util.UUID;
import javax.persistence.*;


@Entity
@Table(name = "orders")
public class Order extends AuditModel {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID orderId;

	private Long userId;
	
	@Column(columnDefinition = "numeric(10,2)")
	private double orderTotal;
	
	@Transient
	private String products;
	
	public UUID getOrderId() {
		return orderId;
	}
	
	public void setOrderId(UUID orderId) {
		this.orderId = orderId;
	}
	
	public Long getUserId() {
		return userId;
	}
	
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public double getOrderTotal() {
		return orderTotal;
	}
	
	public void setOrderTotal(double orderTotal) {
		this.orderTotal = orderTotal;
	}
	
	public String getProducts() {
		return products;
	}
	
	public void setProducts(String products) {
		this.products = products;
	}
}
