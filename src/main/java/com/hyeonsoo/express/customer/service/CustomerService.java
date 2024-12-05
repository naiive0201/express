package com.hyeonsoo.express.customer.service;

import com.hyeonsoo.express.customer.dto.CustomerDto;
import com.hyeonsoo.express.customer.entity.CustomerEntity;
import com.hyeonsoo.express.customer.repo.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public Iterable<CustomerEntity> getAll() {
        return customerRepository.findAll();
    }

    public CustomerEntity getById(Long id) {
        return customerRepository.findById(id).orElseThrow(() -> new RuntimeException("User Not Found"));
    }

    public Iterable<CustomerEntity> getAllByName(String name) {
        return customerRepository.findAllByName(name);
    }

    public CustomerEntity createCustomer(CustomerDto customerDto) {
        if (customerAlreadyExist(customerDto)) {
            throw new RuntimeException("A customer to be inserted already exist");
        }

        CustomerEntity newCustomer = new CustomerEntity();
        newCustomer.setName(customerDto.getName());
        newCustomer.setAddress(customerDto.getAddress());
        newCustomer.setPhone(customerDto.getPhone());
        newCustomer.setRecommendedBy(customerDto.getRecommendedBy());

        return customerRepository.save(newCustomer);
    }

    private boolean customerAlreadyExist(CustomerDto customerDto) {
        CustomerEntity exists = customerRepository.findByNameAndPhone(customerDto.getName(), customerDto.getPhone());
        return (exists != null);
    }

    public CustomerEntity updateCustomer(CustomerDto customerDto) {
        if (!customerAlreadyExist(customerDto)) {
            throw new RuntimeException("A customer to be updated does not exist");
        }

        CustomerEntity updatedCustomer = new CustomerEntity();
        updatedCustomer.setName(customerDto.getName());
        updatedCustomer.setAddress(customerDto.getAddress());
        updatedCustomer.setPhone(customerDto.getPhone());
        updatedCustomer.setRecommendedBy(customerDto.getRecommendedBy());

        return customerRepository.save(updatedCustomer);
    }

    public void deleteCustomer(CustomerDto customerDto) {
        if (!customerAlreadyExist(customerDto)) {
            throw new RuntimeException("A customer to be deleted does not exist");
        }

        CustomerEntity deletedCustomer = new CustomerEntity();
        deletedCustomer.setName(customerDto.getName());
        deletedCustomer.setAddress(customerDto.getAddress());
        deletedCustomer.setPhone(customerDto.getPhone());
        deletedCustomer.setRecommendedBy(customerDto.getRecommendedBy());

        customerRepository.delete(deletedCustomer);
    }
}
