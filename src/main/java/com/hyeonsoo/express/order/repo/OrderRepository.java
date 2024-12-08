package com.hyeonsoo.express.order.repo;

import com.hyeonsoo.express.order.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    Iterable<OrderEntity> findAllByCustomerId(Long customerId);
}
