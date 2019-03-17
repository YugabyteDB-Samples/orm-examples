package com.yugabyte.springdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yugabyte.springdemo.model.OrderLine;

@Repository
public interface OrderLineRepository extends JpaRepository<OrderLine, OrderLine.IdClass> {

}
