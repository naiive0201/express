package com.hyeonsoo.express.order.controller;


import com.hyeonsoo.express.order.dto.OrderDto;
import com.hyeonsoo.express.order.entity.OrderEntity;
import com.hyeonsoo.express.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderEntity> insertOrder(@RequestBody OrderDto orderDto) {
        OrderEntity newOrder = orderService.createOrder(orderDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newOrder);
    }
}
