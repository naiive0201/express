package com.hyeonsoo.express.order.service;

import com.hyeonsoo.express.customer.entity.CustomerEntity;
import com.hyeonsoo.express.order.dto.OrderDto;
import com.hyeonsoo.express.order.entity.OrderEntity;
import com.hyeonsoo.express.order.repo.OrderRepository;
import com.hyeonsoo.express.product.entity.ProductEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderEntity createOrder(OrderDto orderDto) {
        OrderEntity newOrder = new OrderEntity();
        newOrder.setCustomer(new CustomerEntity(orderDto.getCustomer()));
        newOrder.setProduct(new ProductEntity(orderDto.getProduct()));

        return orderRepository.save(newOrder);
    }

    public Iterable<OrderEntity> getOrdersByCustomerId(Long customerId){

        return orderRepository.findAllByCustomerId(customerId);
    }

}
