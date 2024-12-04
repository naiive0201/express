package com.hyeonsoo.express.product.repo;

import com.hyeonsoo.express.product.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
}
