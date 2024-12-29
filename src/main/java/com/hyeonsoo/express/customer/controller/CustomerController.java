package com.hyeonsoo.express.customer.controller;

import com.hyeonsoo.express.customer.dto.CustomerDto;
import com.hyeonsoo.express.customer.entity.CustomerEntity;
import com.hyeonsoo.express.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping
    public ResponseEntity<Iterable<CustomerEntity>> getCustomers(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int size,
        @RequestParam(required = false) String name
    ) {
        if (name == null) {
            return ResponseEntity.ok(customerService.getCustomers(page, size));
        }

        return ResponseEntity.ok(customerService.getCustomersByName(name, page, size));
    }

    @PostMapping
    public ResponseEntity<CustomerEntity> createCustomer(@RequestBody CustomerDto customerDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.createCustomer(customerDto));
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<CustomerEntity> updateCustomer(@PathVariable Long customerId, @RequestBody CustomerDto customerDto) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(customerService.updateCustomer(customerDto));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteCustomer(@RequestBody CustomerDto customerDto) {
        customerService.deleteCustomer(customerDto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
