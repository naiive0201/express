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
import org.springframework.transaction.annotation.Transactional;

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

    /**
     * 고객ID, 주문ID로 조회
     * @param customerId
     * @param orderId
     * @return
     */
    public Order getProductsByCustomerIdAndOrderId(Long customerId, Long orderId) {
        QOrder qOrder = QOrder.order;
        QCustomer qCustomer = QCustomer.customer;
        QProduct qProduct = QProduct.product;

        Tuple query = jpaQueryFactory.select(qOrder, qCustomer, qProduct)
                                    .from(qOrder)
                                    .join(qOrder.customer, qCustomer)
                                    .join(qOrder.product, qProduct)
                                    .where(qOrder.customer.id.eq(customerId)
                                        .and(qOrder.id.eq(orderId))).fetchOne();

        Order order = new Order();
        order.setCustomer(query.get(qCustomer));
        order.setProduct(query.get(qProduct));
        order.setId(query.get(qOrder).getId());
        order.setCreatedAt(query.get(qOrder).getCreatedAt());

        return order;
    }

    /**
     * 고객ID로 pagination 조회
     * @param customerId
     * @param pageable
     * @return
     */
    public PaginatedResponse<Order> getProductsByCustomerId(Long customerId, Pageable pageable) {
        QOrder order = QOrder.order;
        QCustomer customer = QCustomer.customer;
        QProduct product = QProduct.product;

        JPAQuery<Tuple> query = jpaQueryFactory.select(order, customer, product)
                                                .from(order)
                                                .join(order.customer, customer)
                                                .join(order.product, product)
                                                .where(order.customer.id.eq(customerId));

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
            orders.add(newOrder);
        }

        Page<Order> orderPage = new PageImpl<>(orders, pageable, total);

        return new PaginatedResponse<>(
            orderPage.getContent(),
            orderPage.getNumber(),
            orderPage.getTotalPages(),
            orderPage.getTotalElements()
        );
    }

    /**
     * 주문 생성
     * @param orderDto
     * @return
     */
    public Order createOrder(OrderDto orderDto) {
        Order newOrder = new Order();
        newOrder.setCustomer(new Customer(orderDto.getCustomer()));
        newOrder.setProduct(new Product(orderDto.getProduct()));

        return orderRepository.save(newOrder);
    }

    /**
     * 주문 삭제
     * @param id
     */
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}
