package com.hyeonsoo.express.product.service;

import com.hyeonsoo.express.common.dto.PaginatedResponse;
import com.hyeonsoo.express.product.dto.ProductDto;
import com.hyeonsoo.express.product.entity.Product;
import com.hyeonsoo.express.product.entity.QProduct;
import com.hyeonsoo.express.product.repo.ProductRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final JPAQueryFactory jpaQueryFactory;

    public PaginatedResponse<Product> findProductsWithPaginationAndNameFilter(Pageable pageable, String nameFilter) {
        QProduct qProduct = QProduct.product;
        BooleanBuilder whereClause = new BooleanBuilder();

        if (nameFilter != null && !nameFilter.isEmpty()) {
            whereClause.and(qProduct.name.eq(nameFilter));

        }

        List<Product> products = jpaQueryFactory
            .select(qProduct)
            .from(qProduct)
            .where(whereClause)
            .orderBy(qProduct.createdAt.desc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        long total = jpaQueryFactory.select(qProduct.count())
            .from(qProduct)
            .where(whereClause)
            .fetchOne();

        Page<Product> productPage = new PageImpl<>(products, pageable, total);

        return new PaginatedResponse<>(
            productPage.getContent(),
            productPage.getNumber(),
            productPage.getTotalPages(),
            productPage.getTotalElements()
        );
    }

    public Product findProductById(Long id) {
        return findById(id);
    }

    public Product createProduct(ProductDto productDto) {
        Product tobeInserted = new Product();
        tobeInserted.setName(productDto.getName());
        tobeInserted.setPrice(productDto.getPrice());
        tobeInserted.setDescription(productDto.getDescription());

        return productRepository.save(tobeInserted);
    }

    @Transactional
    public Product updateProduct(ProductDto productDto) {
        QProduct qProduct = QProduct.product;

        JPAUpdateClause jpaUpdateClause = jpaQueryFactory.update(qProduct);

        if (productDto.getName() != null && !productDto.getName().isEmpty()) {
            jpaUpdateClause.set(qProduct.name, productDto.getName());
        }

        if (productDto.getPrice() != 0) {
            jpaUpdateClause.set(qProduct.price, productDto.getPrice());
        }

        if (productDto.getDescription() != null && !productDto.getDescription().isEmpty()) {
            jpaUpdateClause.set(qProduct.description, productDto.getDescription());
        }

        long updatedRow = jpaUpdateClause.set(qProduct.updatedAt, LocalDateTime.now())
            .where(qProduct.id.eq(productDto.getId()))
            .execute();

        if (updatedRow == 0) {
            return null;
        }

        return findById(productDto.getId());
    }

    private Product findById(Long id) {
        QProduct qProduct = QProduct.product;

        return jpaQueryFactory
            .selectFrom(qProduct)
            .where(qProduct.id.eq(id)).fetchOne();
    }

    public boolean checkIfProductExists(Long id) {
        Product found = findById(id);

        return (found != null);
    }

    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }
}
