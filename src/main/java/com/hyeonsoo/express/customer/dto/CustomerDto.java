package com.hyeonsoo.express.customer.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CustomerDto {
    private Long id;
    private String name;
    private String phone;
    private String address;
    private String recommendedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
