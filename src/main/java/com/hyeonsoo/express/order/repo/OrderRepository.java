package com.hyeonsoo.express.order.repo;

import com.hyeonsoo.express.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface OrderRepository extends JpaRepository<Order, Long>, QuerydslPredicateExecutor<Order> {
}
