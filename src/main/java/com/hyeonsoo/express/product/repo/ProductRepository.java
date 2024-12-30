package com.hyeonsoo.express.product.repo;

import com.hyeonsoo.express.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductRepository extends JpaRepository<Product, Long> {

    Product findByName(String name);
}
