package com.hyeonsoo.express.customer.repo;

import com.hyeonsoo.express.customer.entity.CustomerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
    Page<CustomerEntity> findByName(String name, Pageable pageable);

    CustomerEntity findByNameAndPhone(String name, String phone);
}
