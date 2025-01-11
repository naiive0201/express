package com.hyeonsoo.express.customer.service;

import com.hyeonsoo.express.common.dto.PaginatedResponse;
import com.hyeonsoo.express.customer.dto.CustomerDto;
import com.hyeonsoo.express.customer.entity.Customer;
import com.hyeonsoo.express.customer.entity.QCustomer;
import com.hyeonsoo.express.customer.repo.CustomerRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {
    
    private final CustomerRepository customerRepository;
    private final JPAQueryFactory jpaQueryFactory;
    
    /**
     * 페이징 처리된 고객리스트 조회하기
     * @param pageable
     * @param nameFilter 이름 필터
     * @return 페이징 처리된 고객리스트
     */
    public PaginatedResponse<Customer> findCustomersWithPaginationAndNameFilter(Pageable pageable, String nameFilter) {
        QCustomer qCustomer = QCustomer.customer;
        BooleanBuilder whereClause = new BooleanBuilder();

        if (nameFilter != null && !nameFilter.isEmpty()) {
            whereClause.and(qCustomer.name.eq(nameFilter));
        }


        List<Customer> customers = jpaQueryFactory
            .select(qCustomer)
            .from(qCustomer)
            .where(whereClause)
            .orderBy(qCustomer.createdAt.desc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();


        long total = jpaQueryFactory.select(qCustomer.count())
            .from(qCustomer)
            .where(whereClause)
            .fetchOne();

        Page<Customer> customerPage = new PageImpl<>(customers, pageable, total);

        return new PaginatedResponse<>(
            customerPage.getContent(),
            customerPage.getNumber(),
            customerPage.getTotalPages(),
            customerPage.getTotalElements()
        );
    }

    /**
     * Id로 고객 찾기
     * @param id
     * @return
     */
    public Customer findCustomerById(Long id) {
        QCustomer qCustomer = QCustomer.customer;
        
        return jpaQueryFactory.selectFrom(qCustomer)
            .where(qCustomer.id.eq(id))
            .fetchOne();
    }
    
    /**
     * 고객 생성하기
     * @param customerDto
     * @return 생성된 고객
     */
    public Customer createCustomer(CustomerDto customerDto) {
        Customer tobeInserted = new Customer();
        tobeInserted.setName(customerDto.getName());
        tobeInserted.setAddress(customerDto.getAddress());
        tobeInserted.setPhone(customerDto.getPhone());
        tobeInserted.setRecommendedBy(customerDto.getRecommendedBy());

        return customerRepository.save(tobeInserted);
    }
    
    /**
     * 고객 업데이트하기
     * @param customerDto
     * @return 업데이트된 고객번호
     */
    @Transactional
    public Customer updateCustomer(CustomerDto customerDto) {
        QCustomer qCustomer = QCustomer.customer;

        JPAUpdateClause jpaUpdateClause = jpaQueryFactory.update(qCustomer);

        if (customerDto.getName() != null && !customerDto.getName().isEmpty()) {
            jpaUpdateClause.set(qCustomer.name, customerDto.getName());
        }

        if (customerDto.getAddress() != null && !customerDto.getAddress().isEmpty()) {
            jpaUpdateClause.set(qCustomer.address, customerDto.getAddress());
        }

        if (customerDto.getPhone() != null && !customerDto.getPhone().isEmpty()) {
            jpaUpdateClause.set(qCustomer.phone, customerDto.getPhone());
        }

        if (customerDto.getRecommendedBy() != null && !customerDto.getRecommendedBy().isEmpty()) {
            jpaUpdateClause.set(qCustomer.recommendedBy, customerDto.getRecommendedBy());
        }

        long updatedRow = jpaUpdateClause.set(qCustomer.updatedAt, LocalDateTime.now())
                                       .where(qCustomer.id.eq(customerDto.getId()))
                                       .execute();

        if (updatedRow == 0) {
            return null;
        }

        return findById(customerDto.getId());
    }

    /**
     * 고객번호 삭제하기
     * @param id
     */
    @Transactional
    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id) ;
    }

    /**
     * id로 고객 존재유무 체크하기
     * @param id
     * @return 고객 존재유무
     */
    public boolean checkIfCustomerExists(Long id) {
        Customer found = findById(id);

        return (found != null);
    }

    /**
     * id로 고객찾기
     * @param id
     * @return id로 찾은 고객
     */
    private Customer findById(Long id) {
        QCustomer qCustomer = QCustomer.customer;

        return jpaQueryFactory
            .selectFrom(qCustomer)
            .where(qCustomer.id.eq(id)).fetchOne();
    }
}
