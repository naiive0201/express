package com.hyeonsoo.express.customer.service;

import com.hyeonsoo.express.common.dto.PaginatedResponse;
import com.hyeonsoo.express.customer.dto.CustomerDto;
import com.hyeonsoo.express.customer.entity.Customer;
import com.hyeonsoo.express.customer.repo.CustomerQueryDslRepository;
import com.hyeonsoo.express.util.EmptyCheckerUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public record CustomerService(CustomerQueryDslRepository customerRepository) {

    public PaginatedResponse<Customer> getPaginatedCustomersByName(String name, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Customer> customerPage = customerRepository.findCustomersWithPaginationAndNameFilter(pageable, name);
        return new PaginatedResponse<>(
            customerPage.getContent(),
            customerPage.getNumber(),
            customerPage.getTotalPages(),
            customerPage.getTotalElements()
        );
    }

    public Page<Customer> getCustomers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return customerRepository.findCustomersWithPaginationAndNameFilter(pageable, null);
    }

    public Customer getByNameAndPhone(CustomerDto customerDto) {
        return customerRepository.findByNameAndPhoneFilter(customerDto.getName(), customerDto.getPhone());
    }

    public Customer createCustomer(CustomerDto customerDto) {
        Customer tobeInserted = new Customer();
        tobeInserted.setName(customerDto.getName());
        tobeInserted.setAddress(customerDto.getAddress());
        tobeInserted.setPhone(customerDto.getPhone());
        tobeInserted.setRecommendedBy(customerDto.getRecommendedBy());

        return customerRepository.insertCustomerIfNotExists(tobeInserted);
    }

//    public Customer updateCustomer(CustomerDto customerDto) {
//        checkIfExists(customerDto);
//
//
//        Customer tobeUpdated = new Customer();
//        tobeUpdated.setName(customerDto.getName());
//        tobeUpdated.setAddress(customerDto.getAddress());
//        tobeUpdated.setPhone(customerDto.getPhone());
//        tobeUpdated.setRecommendedBy(customerDto.getRecommendedBy());
//
//        return customerRepository.save(tobeUpdated);
//    }
//
//    public void deleteCustomer(CustomerDto customerDto) {
//        checkIfExists(customerDto);
//
//        Customer tobeDeleted = new Customer();
//        tobeDeleted.setName(customerDto.getName());
//        tobeDeleted.setAddress(customerDto.getAddress());
//        tobeDeleted.setPhone(customerDto.getPhone());
//        tobeDeleted.setRecommendedBy(customerDto.getRecommendedBy());
//
//        customerRepository.delete(tobeDeleted);
//    }
//
//    private void checkIfExists(CustomerDto customerDto) {
//        Customer customerExists = getByNameAndPhone(customerDto);
//        if (EmptyCheckerUtil.notExists(customerExists)) {
//            throw new RuntimeException("A customer to be deleted does not exist");
//        }
//    }
}
