package com.hyeonsoo.express.product.service;

import com.hyeonsoo.express.product.dto.ProductDto;
import com.hyeonsoo.express.product.entity.ProductEntity;
import com.hyeonsoo.express.product.repo.ProductRepository;
import com.hyeonsoo.express.util.EmptyCheckerUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;

@Service
public record ProductService(ProductRepository productRepository) {
    public Page<ProductEntity> getProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findAll(pageable);
    }

    public ProductEntity getByName(String name) {
        return productRepository.findByName(name);
    }

    public ProductEntity createProduct(ProductDto productDto) {

        ProductEntity existsProduct = getByName(productDto.getName());

        if (EmptyCheckerUtil.exists(existsProduct)) {
            throw new RuntimeException("Product Already Exist");
        }

        ProductEntity tobeInserted = new ProductEntity();
        tobeInserted.setName(productDto.getName());
        tobeInserted.setPrice(productDto.getPrice());
        tobeInserted.setDescription(productDto.getDescription());

        return productRepository.save(tobeInserted);
    }

    public ProductEntity updateProduct(ProductDto productDto) {
        checkIfExists(productDto);

        ProductEntity tobeUpdated = new ProductEntity();
        tobeUpdated.setName(productDto.getName());
        tobeUpdated.setPrice(productDto.getPrice());
        tobeUpdated.setDescription(productDto.getDescription());

        return productRepository.save(tobeUpdated);
    }

    public void deleteProduct(ProductDto productDto) {
        checkIfExists(productDto);

        ProductEntity tobeDeleted = new ProductEntity();
        tobeDeleted.setName(productDto.getName());
        tobeDeleted.setPrice(productDto.getPrice());
        tobeDeleted.setDescription(productDto.getDescription());

        productRepository.delete(tobeDeleted);
    }

    private void checkIfExists(ProductDto productDto) {
        ProductEntity existsProduct = getByName(productDto.getName());

        if (EmptyCheckerUtil.notExists(existsProduct)) {
            throw new RuntimeException("Product not Exist");
        }
    }

}
