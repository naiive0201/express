package com.hyeonsoo.express.product.entity;

import com.hyeonsoo.express.product.dto.ProductDto;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "products")
@Data
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String name;
    private int price;
    private String description;
    @CreatedDate
    private LocalDateTime createdAt;

    public ProductEntity(ProductDto productDto) {
        this.id = productDto.getId();
        this.name = productDto.getName();
        this.price = productDto.getPrice();
        this.description = productDto.getDescription();
        this.createdAt = productDto.getCreatedAt();
    }
}
