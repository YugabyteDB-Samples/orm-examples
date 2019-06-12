package com.yugabyte.springdemo.model;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "orderline")
@IdClass(OrderLine.IdClass.class)
public class OrderLine {
	
	@Id
	private UUID orderId;
	
	@Id
	@ManyToOne
	@JoinColumn(name="orderId", referencedColumnName="orderId", insertable=false, updatable=false)
	@OnDelete(action=OnDeleteAction.CASCADE)
	private Order order;
	
	@Id
	private Long productId;

	@Id
	@ManyToOne
	@JoinColumn(name="productId", referencedColumnName="productId", insertable=false, updatable=false)
	//@OnDelete(action=OnDeleteAction.CASCADE)
	private Product product;
	
	private Integer units;

	public OrderLine() {
	}
	
	public OrderLine(UUID orderId, Long productId, Integer units) {
		this.orderId = orderId;
		this.productId = productId;
		this.units = units;
	}
	
	public UUID getOrderId() {
		return this.orderId;
	}
	
	public void setOrderId(UUID orderId) {
		this.orderId = orderId;
	}
	
	public Long getProductId() {
		return productId;
	}
	
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	
	public Integer getUnits() {
		return units;
	}
	
	public void setUnits(Integer units) {
		this.units = units;
	}
	
	public static class IdClass implements Serializable {
		private UUID orderId;
		private Long productId;
	}
}
