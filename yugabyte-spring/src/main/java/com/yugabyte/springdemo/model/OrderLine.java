package com.yugabyte.springdemo.model;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.*;

@Entity
@Table(name = "orderline")
@IdClass(OrderLine.IdClass.class)
public class OrderLine {
	@Id
	private UUID orderId;
	
	@Id
	private UUID productId;

	public OrderLine() {
		
	}
	
	public OrderLine(UUID orderId, UUID productId) {
		this.orderId = orderId;
		this.productId = productId;
	}
	
	public UUID getOrderId() {
		return orderId;
	}
	
	public void setOrderId(UUID orderId) {
		this.orderId = orderId;
	}
	
	public UUID getProductId() {
		return productId;
	}
	
	public void setProductId(UUID productId) {
		this.productId = productId;
	}
	
    public static class IdClass implements Serializable {
        private UUID orderId;
        private UUID productId;
    }
}
