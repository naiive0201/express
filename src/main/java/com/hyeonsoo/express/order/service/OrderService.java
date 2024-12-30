package com.hyeonsoo.express.order.service;

import com.hyeonsoo.express.customer.entity.Customer;
import com.hyeonsoo.express.order.dto.OrderDto;
import com.hyeonsoo.express.order.entity.Order;
import com.hyeonsoo.express.order.repo.OrderRepository;
import com.hyeonsoo.express.product.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public Iterable<Order> getOrdersByCustomerId(Long customerId){

        return orderRepository.findAllByCustomerId(customerId);
    }

    public Order createOrder(OrderDto orderDto) {
        Order newOrder = new Order();
        newOrder.setCustomer(new Customer(orderDto.getCustomer()));
        newOrder.setProduct(new Product(orderDto.getProduct()));

        return orderRepository.save(newOrder);
    }


    public void deleteOrder(OrderDto orderDto) {
        Order deletedOrder = new Order();
        deletedOrder.setId(orderDto.getId());

        orderRepository.delete(deletedOrder);
    }
}
