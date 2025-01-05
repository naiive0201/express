package com.hyeonsoo.express.order.service;

import com.hyeonsoo.express.common.dto.PaginatedResponse;
import com.hyeonsoo.express.customer.entity.Customer;
import com.hyeonsoo.express.customer.entity.QCustomer;
import com.hyeonsoo.express.order.dto.OrderDto;
import com.hyeonsoo.express.order.entity.Order;
import com.hyeonsoo.express.order.entity.QOrder;
import com.hyeonsoo.express.order.repo.OrderRepository;
import com.hyeonsoo.express.product.entity.Product;
import com.hyeonsoo.express.product.entity.QProduct;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final JPAQueryFactory jpaQueryFactory;

    /**
     * id로 주문 조회하기
     * @param id
     * @return
     */
    public Order getOrderById(Long id){
        QOrder order = QOrder.order;
        
        return jpaQueryFactory.selectFrom(order)
            .where(order.id.eq(id))
            .fetchOne();
    }

    public PaginatedResponse<Order> getProductsByCustomerId(Long customerId, Long orderId, Pageable pageable) {
        QOrder order = QOrder.order;
        QCustomer customer = QCustomer.customer;
        QProduct product = QProduct.product;

        JPAQuery<Tuple> query = jpaQueryFactory.select(order, customer, product)
                                            .from(order)
                                            .join(order.customer, customer)
                                            .join(order.product, product)
                                            .where(order.customer.id.eq(customerId)
                                                .and(order.id.eq(orderId)));

        long total = query.fetch().size();
        List<Tuple> tuples  = query
                                .offset(pageable.getOffset())
                                .limit(pageable.getPageSize())
                                .fetch();

        List<Order> orders = new ArrayList<>();

        Order newOrder = null;
        for (Tuple cur : tuples) {
            Order curOrder = cur.get(order);
            newOrder = new Order();
            newOrder.setCustomer(curOrder.getCustomer());
            newOrder.setProduct(curOrder.getProduct());
            newOrder.setId(curOrder.getId());
            newOrder.setCreatedAt(curOrder.getCreatedAt());
        }

        Page<Order> orderPage = new PageImpl<>(orders, pageable, total);


        return new PaginatedResponse<>(
            orderPage.getContent(),
            orderPage.getNumber(),
            orderPage.getTotalPages(),
            orderPage.getTotalElements()
        );
    }

//    public Order getProductByOrderId(Long orderId) {
//
//    }

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
