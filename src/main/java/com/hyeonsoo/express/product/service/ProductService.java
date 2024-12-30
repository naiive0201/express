package com.hyeonsoo.express.product.service;

import com.hyeonsoo.express.product.dto.ProductDto;
import com.hyeonsoo.express.product.entity.Product;
import com.hyeonsoo.express.product.repo.ProductRepository;
import com.hyeonsoo.express.util.EmptyCheckerUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;

@Service
public record ProductService(ProductRepository productRepository) {
    public Page<Product> getProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findAll(pageable);
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
