package com.hyeonsoo.express.order.controller;


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

    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@RequestBody OrderDto orderDto, @PathVariable Long id) {
        boolean orderFound = orderService.checkIfCustomerExists(id);

        if (!orderFound) {
            return ResponseEntity.notFound().build();
        }

        orderDto.setId(id);

        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
