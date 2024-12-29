package com.hyeonsoo.express.order.dto;


import com.hyeonsoo.express.customer.dto.CustomerDto;
import com.hyeonsoo.express.product.dto.ProductDto;
import lombok.Data;

@Data
public class OrderDto {
    private Long id;
    private CustomerDto customer;
    private ProductDto product;

    public OrderDto(CustomerDto customer, ProductDto product) {
        this.customer = customer;
        this.product = product;
    }
}
