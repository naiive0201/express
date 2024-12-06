package com.hyeonsoo.express.customer.service;

import com.hyeonsoo.express.customer.dto.CustomerDto;
import com.hyeonsoo.express.customer.entity.CustomerEntity;
import com.hyeonsoo.express.customer.repo.CustomerRepository;
import com.hyeonsoo.express.util.EmptyCheckerUtil;
import org.springframework.stereotype.Service;

@Service
public record CustomerService(CustomerRepository customerRepository) {
    public Iterable<CustomerEntity> getAll() {
        return customerRepository.findAll();
    }

    public CustomerEntity getById(Long id) {
        return customerRepository.findById(id).orElseThrow(() -> new RuntimeException("User Not Found"));
    }

    public CustomerEntity getByName(String name) {
        return customerRepository.findByName(name);
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
        CustomerEntity customerExists = getByNameAndPhone(customerDto);
        if (EmptyCheckerUtil.notExists(customerExists)) {
            throw new RuntimeException("A customer to be updated does not exist");
        }

        CustomerEntity tobeUpdated = new CustomerEntity();
        tobeUpdated.setName(customerDto.getName());
        tobeUpdated.setAddress(customerDto.getAddress());
        tobeUpdated.setPhone(customerDto.getPhone());
        tobeUpdated.setRecommendedBy(customerDto.getRecommendedBy());

        return customerRepository.save(tobeUpdated);
    }

    public void deleteCustomer(CustomerDto customerDto) {
        CustomerEntity customerExists = getByNameAndPhone(customerDto);
        if (EmptyCheckerUtil.notExists(customerExists)) {
            throw new RuntimeException("A customer to be deleted does not exist");
        }

        CustomerEntity tobeDeleted = new CustomerEntity();
        tobeDeleted.setName(customerDto.getName());
        tobeDeleted.setAddress(customerDto.getAddress());
        tobeDeleted.setPhone(customerDto.getPhone());
        tobeDeleted.setRecommendedBy(customerDto.getRecommendedBy());

        customerRepository.delete(tobeDeleted);
    }
}
