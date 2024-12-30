package com.hyeonsoo.express.order.repo;

import com.hyeonsoo.express.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Iterable<Order> findAllByCustomerId(Long customerId);
}
