package com.hyeonsoo.express.product.service;

import com.hyeonsoo.express.common.dto.PaginatedResponse;
import com.hyeonsoo.express.product.dto.ProductDto;
import com.hyeonsoo.express.product.entity.Product;
import com.hyeonsoo.express.product.entity.QProduct;
import com.hyeonsoo.express.product.repo.ProductRepository;
import com.hyeonsoo.express.util.EmptyCheckerUtil;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;

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

    public Product getByName(String name) {
        return productRepository.findByName(name);
    }

    public Product createProduct(ProductDto productDto) {

        Product existsProduct = getByName(productDto.getName());

        if (EmptyCheckerUtil.exists(existsProduct)) {
            throw new RuntimeException("Product Already Exist");
        }

        Product tobeInserted = new Product();
        tobeInserted.setName(productDto.getName());
        tobeInserted.setPrice(productDto.getPrice());
        tobeInserted.setDescription(productDto.getDescription());

        return productRepository.save(tobeInserted);
    }

    public Product updateProduct(ProductDto productDto) {
        checkIfExists(productDto);

        Product tobeUpdated = new Product();
        tobeUpdated.setName(productDto.getName());
        tobeUpdated.setPrice(productDto.getPrice());
        tobeUpdated.setDescription(productDto.getDescription());

        return productRepository.save(tobeUpdated);
    }

    public void deleteProduct(ProductDto productDto) {
        checkIfExists(productDto);

        Product tobeDeleted = new Product();
        tobeDeleted.setName(productDto.getName());
        tobeDeleted.setPrice(productDto.getPrice());
        tobeDeleted.setDescription(productDto.getDescription());

        productRepository.delete(tobeDeleted);
    }

    private void checkIfExists(ProductDto productDto) {
        Product existsProduct = getByName(productDto.getName());

        if (EmptyCheckerUtil.notExists(existsProduct)) {
            throw new RuntimeException("Product not Exist");
        }
    }

}
