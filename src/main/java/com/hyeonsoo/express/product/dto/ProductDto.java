package com.hyeonsoo.express.product.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProductDto {

    private Long id;
    private String name;
    private int price;
    private String description;
    private LocalDateTime createdAt;

}
