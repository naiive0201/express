package com.hyeonsoo.express.product.controller;


import com.hyeonsoo.express.product.dto.ProductDto;
import com.hyeonsoo.express.product.entity.Product;
import com.hyeonsoo.express.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<Page<Product>> getProducts(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int size
    ) {
        return ResponseEntity.ok(productService.getProducts(page, size));
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody ProductDto productDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(productDto));
    }

    @PutMapping
    public ResponseEntity<Product> updateProduct(@RequestBody ProductDto productDto) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(productService.updateProduct(productDto));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteProduct(@RequestBody ProductDto productDto) {
        productService.deleteProduct(productDto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
