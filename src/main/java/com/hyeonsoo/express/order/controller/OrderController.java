package com.hyeonsoo.express.order.controller;


import com.hyeonsoo.express.common.dto.PaginatedResponse;
import com.hyeonsoo.express.order.dto.OrderDto;
import com.hyeonsoo.express.order.entity.Order;
import com.hyeonsoo.express.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        Order orderById = orderService.getOrderById(id);

        if (orderById == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(orderById);
    }

    @PostMapping
    public ResponseEntity<Long> insertOrder(@RequestBody OrderDto orderDto) {
        Order newOrder = orderService.createOrder(orderDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newOrder.getId());
    }


    @DeleteMapping
    public ResponseEntity<Void> deleteOrder(@RequestBody OrderDto orderDto) {
        orderService.deleteOrder(orderDto);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
