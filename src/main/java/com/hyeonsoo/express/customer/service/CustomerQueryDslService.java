package com.hyeonsoo.express.customer.service;

import com.hyeonsoo.express.common.dto.PaginatedResponse;
import com.hyeonsoo.express.customer.dto.CustomerDto;
import com.hyeonsoo.express.customer.entity.Customer;
import com.hyeonsoo.express.customer.entity.QCustomer;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CustomerQueryDslService {

  private final JPAQueryFactory queryFactory;

  public PaginatedResponse<Customer> findCustomersWithPaginationAndNameFilter(Pageable pageable, String nameFilter) {
    QCustomer customer = QCustomer.customer;

    JPAQuery<Customer> query = queryFactory.selectFrom(customer);

    if (nameFilter != null && !nameFilter.isEmpty()) {
      query.where(customer.name.eq(nameFilter));
    }

    // apply pagination
    long total = query.fetch().size();
    List<Customer> customers = query
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();

    Page<Customer> customerPage = new PageImpl<>(customers, pageable, total);

    return new PaginatedResponse<>(
        customerPage.getContent(),
        customerPage.getNumber(),
        customerPage.getTotalPages(),
        customerPage.getTotalElements()
    );
  }

  public Customer findByNameAndPhoneFilter(String nameFilter, String phoneFilter) {
    QCustomer customer = QCustomer.customer;

    JPAQuery<Customer> query = queryFactory.selectFrom(customer)
        .where(customer.name.eq(nameFilter)
            .and(customer.phone.eq(phoneFilter))
        );

    return query.fetchFirst();
  }
}
