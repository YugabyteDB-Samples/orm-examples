package com.yugabyte.springdemo.controller;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.yugabyte.springdemo.exception.ResourceNotFoundException;
import com.yugabyte.springdemo.model.*;
import com.yugabyte.springdemo.repository.*;

@RestController
public class OrderController {
	@Autowired
    private OrderRepository orderRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	OrderLineRepository orderLineRepository;

    @GetMapping("/orders")
    public Page<Order> getOrders(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    @PostMapping("/orders")
    public Order createOrder(@Valid @RequestBody Order order) {

    	double orderTotal = 0.0;
    	for (Order.Product orderProduct : order.getProducts()) {
    		orderTotal += productRepository.findById(orderProduct.getProductId())
    				.map(product -> { return product.getPrice() * orderProduct.getUnits(); 
    				}).orElseThrow(() -> new ResourceNotFoundException("Product not found with Id: " + orderProduct.getProductId()));
    	}

    	order.setOrderTotal(orderTotal);
    	order.setUser(userRepository.findById(order.getUserId()).map(user -> {
            return user;
        }).orElseThrow(() -> new ResourceNotFoundException("UserId " + order.getUserId() + " not found")));

    	Order newOrder = orderRepository.save(order);
    	
    	for (Order.Product orderProduct : order.getProducts()) {
    		orderLineRepository.save(new OrderLine(newOrder.getOrderId(), orderProduct.getProductId(), orderProduct.getUnits()));
    	}
        
    	return newOrder;
    }

    @PutMapping("/orders/{orderId}")
    public Order updateOrder(@PathVariable UUID orderId,
                                   @Valid @RequestBody Order orderRequest) {

    	for (OrderLine orderLine : orderLineRepository.findByOrderId(orderId)) {
    		orderLineRepository.delete(orderLine);
    	}
    	
    	for (Order.Product orderProduct : orderRequest.getProducts()) {
    		orderLineRepository.save(new OrderLine(orderId, orderProduct.getProductId(), orderProduct.getUnits()));
    	}

    	return orderRepository.findById(orderId)
                .map(order -> {
                	double orderTotal = 0.0;
                	for (Order.Product orderProduct : orderRequest.getProducts()) {
                		orderTotal += productRepository.findById(orderProduct.getProductId())
                				.map(product -> { return product.getPrice() * orderProduct.getUnits(); 
                				}).orElseThrow(() -> new ResourceNotFoundException("Product not found with Id: " + orderProduct.getProductId()));
                	}

                	order.setUser(userRepository.findById(orderRequest.getUserId()).map(user -> {
                        return user;
                    }).orElseThrow(() -> new ResourceNotFoundException("UserId " + order.getUserId() + " not found")));
                	
                    order.setOrderTotal(orderTotal);
                    return orderRepository.save(order);
                }).orElseThrow(() -> new ResourceNotFoundException("Order not found with id " + orderId));
    }

    @DeleteMapping("/orders/{orderId}")
    public ResponseEntity<?> deleteOrder(@PathVariable UUID orderId) {
        return orderRepository.findById(orderId)
                .map(order -> {
                    orderRepository.delete(order);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Order not found with id " + orderId));
    }
}
