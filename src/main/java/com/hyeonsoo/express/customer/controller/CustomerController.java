package com.hyeonsoo.express.customer.controller;

import com.hyeonsoo.express.common.dto.PaginatedResponse;
import com.hyeonsoo.express.customer.dto.CustomerDto;
import com.hyeonsoo.express.customer.entity.Customer;
import com.hyeonsoo.express.customer.service.CustomerService;
import com.hyeonsoo.express.order.entity.Order;
import com.hyeonsoo.express.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;
    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<PaginatedResponse<Customer>> getCustomers(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int size,
        @RequestParam(required = false) String name
    ) {
        PaginatedResponse<Customer> customersByName = customerService.findCustomersWithPaginationAndNameFilter(PageRequest.of(page, size), name);
        return ResponseEntity.ok(customersByName);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        Customer customerById = customerService.findCustomerById(id);

        if (customerById == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(customerById);
    }

    @GetMapping("/{customerId}/orders")
    public ResponseEntity<PaginatedResponse<Order>> getOrdersId(@PathVariable Long customerId,
                                            @RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "5") int size) {
        Customer customerById = customerService.findCustomerById(customerId);

        if (customerById == null) {
            return ResponseEntity.notFound().build();
        }

        PaginatedResponse<Order> ordersFound = orderService.getProductsByCustomerId(customerId, PageRequest.of(page, size));
        return ResponseEntity.ok(ordersFound);

    }

    @GetMapping("/{customerId}/orders/{orderId}")
    public ResponseEntity<Order> getCustomerOrder(@PathVariable Long customerId,
                                                  @PathVariable Long orderId) {
        if (customerId == null || orderId == null) {
            return ResponseEntity.notFound().build();
        }
        Customer customerById = customerService.findCustomerById(customerId);

        if (customerById == null) {
            return ResponseEntity.notFound().build();
        }

        Order orderFound = orderService.getProductsByCustomerIdAndOrderId(customerId, orderId);

        return ResponseEntity.ok(orderFound);
    }

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody CustomerDto customerDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.createCustomer(customerDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@RequestBody CustomerDto customerDto, @PathVariable Long id) {
        boolean found = customerService.checkIfCustomerExists(id);

        if (!found) {
            return ResponseEntity.notFound().build();
        }

        customerDto.setId(id);

        Customer updated = customerService.updateCustomer(customerDto);

        if (updated == null) {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
        }

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        boolean found = customerService.checkIfCustomerExists(id);

        if (!found) {
            return ResponseEntity.notFound().build();
        }

        customerService.deleteCustomer(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
