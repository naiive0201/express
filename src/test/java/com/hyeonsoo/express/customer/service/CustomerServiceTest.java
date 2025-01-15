package com.hyeonsoo.express.customer.service;

import com.hyeonsoo.express.common.dto.PaginatedResponse;
import com.hyeonsoo.express.customer.dto.CustomerDto;
import com.hyeonsoo.express.customer.entity.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomerServiceTest {
  @Autowired
  private CustomerService customerService;

  @Test
  void createCustomer() {
    CustomerDto req = new CustomerDto();
    req.setName("지인구");
    req.setPhone("01033334444");
    req.setAddress("경기도 부천시 역곡로 4");
    req.setRecommendedBy("지나인");
    Customer res = customerService.createCustomer(req);
    assertTrue(res != null, "created customer");
  }

  @Test
  void findCustomersWithPaginationAndNameFilter() {
    PaginatedResponse<Customer> res =
        customerService.findCustomersWithPaginationAndNameFilter(PageRequest.of(0, 5), null);
    assertTrue(res.getContent().size() > 0, "have contents");
  }

  @Test
  void findCustomerById() {
    Customer res = customerService.findCustomerById(1L);
    assertTrue(res != null, "have customer id");
  }
  
  @Test
  void updateCustomer() {
    CustomerDto req = new CustomerDto();
    req.setId(1L);
    req.setName("인생살이");
    req.setPhone("01033335555");
    req.setAddress("경기도 부천시 역곡로 5");
    req.setRecommendedBy("인생굿");
    Customer updated = customerService.updateCustomer(req);

    assertTrue(updated != null, "updated customer");
  }

  @Test
  void checkIfCustomerExists() {
    boolean exists = customerService.checkIfCustomerExists(2L);
    assertTrue(exists, "customer exists");
  }

  @Test
  void deleteCustomer() {
    customerService.deleteCustomer(1L);
    Customer res = customerService.findCustomerById(1L);
    assertTrue(res == null, "deleted customer");
  }
}