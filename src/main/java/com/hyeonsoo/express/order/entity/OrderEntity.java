package com.hyeonsoo.express.order.entity;


import com.hyeonsoo.express.customer.entity.CustomerEntity;
import com.hyeonsoo.express.product.entity.ProductEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "orders")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "customers_id")
    private CustomerEntity custId;

    @ManyToOne
    @JoinColumn(name = "products_id")
    private ProductEntity productId;
}
