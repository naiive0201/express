package com.hyeonsoo.express.customer.repo;

import com.hyeonsoo.express.customer.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
    public Iterable<CustomerEntity> findAllByName(String name);

    public CustomerEntity findByNameAndPhone(String name, String phone);
}
