package com.hyeonsoo.express.product.service;

import com.hyeonsoo.express.product.dto.ProductDto;
import com.hyeonsoo.express.product.entity.ProductEntity;
import com.hyeonsoo.express.product.repo.ProductRepository;
import com.hyeonsoo.express.util.EmptyCheckerUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Iterable<ProductEntity> getAll() {
        return productRepository.findAll();
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
