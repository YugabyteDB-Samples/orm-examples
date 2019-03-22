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
import com.yugabyte.springdemo.model.OrderLine.IdClass;
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
    	
    	Long userId = userRepository.findById(order.getUserId())
    			.map(user -> {return user.getUserId(); 
    			}).orElseThrow(() -> new ResourceNotFoundException("User not found with Id: " + order.getUserId()));

    	double orderTotal = 0.0;
    	for (String productId : order.getProducts().split(",")) {
    		orderTotal += productRepository.findById(Long.parseLong(productId))
    				.map(product -> { return product.getPrice(); 
    				}).orElseThrow(() -> new ResourceNotFoundException("Product not found with Id: " + productId));
    	}
    	
    	order.setOrderTotal(orderTotal);
    	Order newOrder = orderRepository.save(order);
    	
    	for (String productId : order.getProducts().split(",")) {
    		orderLineRepository.save(new OrderLine(newOrder.getOrderId(), Long.parseLong(productId)));
    	}
        
    	return newOrder;
    }

    @PutMapping("/orders/{orderId}")
    public Order updateProduct(@PathVariable UUID orderId,
                                   @Valid @RequestBody Order orderRequest) {
        return orderRepository.findById(orderId)
                .map(order -> {
                    order.setUserId(orderRequest.getUserId());
                    order.setOrderTime(orderRequest.getOrderTime());
                    order.setOrderTotal(orderRequest.getOrderTotal());
                    return orderRepository.save(order);
                }).orElseThrow(() -> new ResourceNotFoundException("Order not found with id " + orderId));
    }

    @DeleteMapping("/orders/{orderId}")
    public ResponseEntity<?> deleteProduct(@PathVariable UUID orderId) {
    	
    	for (OrderLine orderLine : orderLineRepository.findByOrderId(orderId)) {
    		orderLineRepository.delete(orderLine);
    	}
    		
        return orderRepository.findById(orderId)
                .map(order -> {
                    orderRepository.delete(order);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Order not found with id " + orderId));
    }
}
