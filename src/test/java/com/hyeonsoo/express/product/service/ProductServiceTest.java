package com.hyeonsoo.express.product.service;

import com.hyeonsoo.express.common.dto.PaginatedResponse;
import com.hyeonsoo.express.product.dto.ProductDto;
import com.hyeonsoo.express.product.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductServiceTest {
  @Autowired
  private ProductService productService;

  @Test
  void createProduct() {
    ProductDto tobeCreated = new ProductDto();
    tobeCreated.setName("복숭아 1박스#1");
    tobeCreated.setPrice(30000);
    tobeCreated.setDescription("복숭아 1박스");
    Product createdRes = productService.createProduct(tobeCreated);

    assertNotNull(createdRes, "Product created");
  }

  @Test
  void findProductsWithPaginationAndNameFilter() {
    PaginatedResponse<Product> products = productService.findProductsWithPaginationAndNameFilter(PageRequest.of(0, 5), null);

    assertTrue(products.getTotalItems() > 0L, "products exists");
  }

  @Test
  void findProductById() {
    Product found = productService.findProductById(1L);
    assertNotNull(found, "A product 1 exist");
  }

  @Test
  void updateProduct() {
    ProductDto updated = new ProductDto();
    updated.setId(1L);
    updated.setName("복숭아 2박스 #1");
    updated.setDescription("Hello");
    updated.setPrice(40000);
    Product updatedRes = productService.updateProduct(updated);

    assertNotNull(updatedRes, "product updated");
  }

  @Test
  void checkIfProductExists() {

    boolean existsRes = productService.checkIfProductExists(2L);

    assertTrue(existsRes, "product 1 exists");
  }

  @Test
  void deleteProductById() {
    productService.deleteProductById(1L);

    assertNull(productService.findProductById(1L), "product deleted");
  }
}