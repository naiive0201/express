package com.hyeonsoo.express.customer.service;

import com.hyeonsoo.express.customer.dto.CustomerDto;
import com.hyeonsoo.express.customer.entity.CustomerEntity;
import com.hyeonsoo.express.customer.repo.CustomerRepository;
import com.hyeonsoo.express.util.EmptyCheckerUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public record CustomerService(CustomerRepository customerRepository) {

    public Page<CustomerEntity> getCustomersByName(String name, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return customerRepository.findByName(name, pageable);
    }

    public Page<CustomerEntity> getCustomers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return customerRepository.findAll(pageable);
    }

    public CustomerEntity getById(Long id) {
        return customerRepository.findById(id).orElseThrow(() -> new RuntimeException("User Not Found"));
    }

    public CustomerEntity getByNameAndPhone(CustomerDto customerDto) {
        return customerRepository.findByNameAndPhone(customerDto.getName(), customerDto.getPhone());
    }

    public CustomerEntity createCustomer(CustomerDto customerDto) {
        CustomerEntity customerExists = getByNameAndPhone(customerDto);
        if (EmptyCheckerUtil.exists(customerExists)) {
            throw new RuntimeException("A customer to be inserted already exist");
        }

        CustomerEntity tobeInserted = new CustomerEntity();
        tobeInserted.setName(customerDto.getName());
        tobeInserted.setAddress(customerDto.getAddress());
        tobeInserted.setPhone(customerDto.getPhone());
        tobeInserted.setRecommendedBy(customerDto.getRecommendedBy());

        return customerRepository.save(tobeInserted);
    }

    public CustomerEntity updateCustomer(CustomerDto customerDto) {
        checkIfExists(customerDto);

        CustomerEntity tobeUpdated = new CustomerEntity();
        tobeUpdated.setName(customerDto.getName());
        tobeUpdated.setAddress(customerDto.getAddress());
        tobeUpdated.setPhone(customerDto.getPhone());
        tobeUpdated.setRecommendedBy(customerDto.getRecommendedBy());

        return customerRepository.save(tobeUpdated);
    }

    public void deleteCustomer(CustomerDto customerDto) {
        checkIfExists(customerDto);

        CustomerEntity tobeDeleted = new CustomerEntity();
        tobeDeleted.setName(customerDto.getName());
        tobeDeleted.setAddress(customerDto.getAddress());
        tobeDeleted.setPhone(customerDto.getPhone());
        tobeDeleted.setRecommendedBy(customerDto.getRecommendedBy());

        customerRepository.delete(tobeDeleted);
    }

    private void checkIfExists(CustomerDto customerDto) {
        CustomerEntity customerExists = getByNameAndPhone(customerDto);
        if (EmptyCheckerUtil.notExists(customerExists)) {
            throw new RuntimeException("A customer to be deleted does not exist");
        }
    }
}
