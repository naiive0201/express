package com.hyeonsoo.express.customer.entity;

import com.hyeonsoo.express.customer.dto.CustomerDto;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "customers")
@Data
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String phone;
    @Column(nullable = false)
    private String address;
    @Column(nullable = true)
    private String recommendedBy;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    public Customer(CustomerDto customerDto) {
        this.id = customerDto.getId();
        this.name = customerDto.getName();
        this.phone = customerDto.getPhone();
        this.address = customerDto.getAddress();
        this.recommendedBy = customerDto.getRecommendedBy();
        this.createdAt = customerDto.getCreatedAt();
        this.updatedAt = customerDto.getUpdatedAt();
    }

}
