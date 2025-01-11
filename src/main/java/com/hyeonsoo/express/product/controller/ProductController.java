package com.hyeonsoo.express.product.controller;


import com.hyeonsoo.express.common.dto.PaginatedResponse;
import com.hyeonsoo.express.product.dto.ProductDto;
import com.hyeonsoo.express.product.entity.Product;
import com.hyeonsoo.express.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<PaginatedResponse<Product>> getProducts(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int size,
        @RequestParam String name
    ) {
        return ResponseEntity.ok(productService.findProductsWithPaginationAndNameFilter(PageRequest.of(page, size), name));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product productById = productService.findProductById(id);

        if (productById == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(productById);
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody ProductDto productDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(productDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@RequestBody ProductDto productDto, @PathVariable Long id) {
        boolean found = productService.checkIfProductExists(id);

        if (!found) {
            return ResponseEntity.notFound().build();
        }

        productDto.setId(id);

        Product updated = productService.updateProduct(productDto);

        if (updated == null) {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
        }

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        boolean found = productService.checkIfProductExists(id);

        if (!found) {
            return ResponseEntity.notFound().build();
        }

        productService.deleteProductById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
