package com.yugabyte.springdemo.repository;

import com.yugabyte.springdemo.model.Order;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {

}
