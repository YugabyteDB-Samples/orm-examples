package com.yugabyte.springdemo.model;

import java.util.*;
import javax.persistence.*;

@Entity
@Table(name = "orders")
public class Order extends AuditModel {
	
	public static class Product {
		private Long productId;
		private Integer units;
		
		public Long getProductId() {
			return this.productId;
		}
		
		public Integer getUnits() {
			return this.units;
		}
	}
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID orderId;

	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	
	@Transient
	private Long userId;
	
	@Column(columnDefinition = "numeric(10,2)")
	private double orderTotal;
	
	@Transient
	@ElementCollection
	private Set<Product> products = new HashSet<>();
	
	public UUID getOrderId() {
		return orderId;
	}
	
	public void setOrderId(UUID orderId) {
		this.orderId = orderId;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public Long getUserId() {
		return this.userId;
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
	
	public Set<Product> getProducts() {
		return products;
	}
	
	public void setProducts(Set<Product> products) {
		this.products = products;
	}
}
