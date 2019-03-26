package com.yugabyte.springdemo.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yugabyte.springdemo.model.OrderLine;

@Repository
public interface OrderLineRepository extends JpaRepository<OrderLine, UUID> {
	public List<OrderLine> findByOrderId(UUID orderId);
}
