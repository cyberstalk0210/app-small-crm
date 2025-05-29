package org.example.appsmallcrm.controller;

import lombok.RequiredArgsConstructor;
import org.example.appsmallcrm.entity.DashboardStats;
import org.example.appsmallcrm.entity.Product;
import org.example.appsmallcrm.repo.ProductRepo;
import org.example.appsmallcrm.service.DashboardStatsService;
import org.example.appsmallcrm.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ProductRepo productRepo;

    @GetMapping("/products")
    public List<Product> getProducts() {
        return productService.getProductsList();
    }

    @PostMapping("/add-product")
    public Product createProduct(@RequestBody Product product) {
       return  productService.createProduct(product);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("Product deleted successfully");
    }

    @PostMapping("/edit-product/{id}")
    public ResponseEntity<String> editProduct(@PathVariable Long id, @RequestBody Product product) {
        return (ResponseEntity<String>) productService.editProduct(product, id);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return productRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
