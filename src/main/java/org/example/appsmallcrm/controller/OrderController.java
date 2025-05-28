package org.example.appsmallcrm.controller;

import lombok.RequiredArgsConstructor;
import org.example.appsmallcrm.dto.OrderRequestDTO;
import org.example.appsmallcrm.entity.Order;
import org.example.appsmallcrm.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @PostMapping("/create/order")
    public ResponseEntity<Order> createOrder(
            @RequestHeader("Authorization") String token,
            @RequestBody OrderRequestDTO orderRequest) {
        if (!token.startsWith("Bearer ")) {
            return ResponseEntity.status(401).build();
        }
        Order order = orderService.createOrder(orderRequest);
        return ResponseEntity.ok(order);
    }
}

