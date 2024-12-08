package com.hyeonsoo.express.order.controller;


import com.hyeonsoo.express.order.dto.OrderDto;
import com.hyeonsoo.express.order.entity.OrderEntity;
import com.hyeonsoo.express.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping("{customerId}")
    public ResponseEntity<Iterable<OrderEntity>> getOrdersByCustomerId(@PathVariable Long customerId) {
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(orderService.getOrdersByCustomerId(customerId));
    }

    @PostMapping
    public ResponseEntity<OrderEntity> insertOrder(@RequestBody OrderDto orderDto) {
        OrderEntity newOrder = orderService.createOrder(orderDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newOrder);
    }
}
