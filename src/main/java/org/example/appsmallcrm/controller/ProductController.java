package org.example.appsmallcrm.controller;

import org.example.appsmallcrm.entity.Product;
import org.example.appsmallcrm.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @PostMapping("/add-product")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addProduct(
            @RequestParam("name") String name,
            @RequestParam("price") Double price,
            @RequestParam("stock") Integer stock,
            @RequestParam("status") String status,
            @RequestParam(value = "image", required = false) MultipartFile image) throws IOException {

        Product product = Product.builder()
                .name(name)
                .price(price)
                .stock(stock)
                .status(status)
                .build();

        Product savedProduct = productService.createProduct(product, image);
        return ResponseEntity.ok("Mahsulot muvaffaqiyatli qo'shildi: " + savedProduct.getId());
    }

    @PutMapping("/edit-product/{id}")
    public ResponseEntity<?> editProduct(
            @PathVariable Long id,
            @RequestParam("name") String name,
            @RequestParam("price") Double price,
            @RequestParam("stock") Integer stock,
            @RequestParam("status") String status,
            @RequestParam(value = "image", required = false) MultipartFile image) throws IOException {

        Product updatedProduct = Product.builder()
                .name(name)
                .price(price)
                .stock(stock)
                .status(status)
                .build();

        return productService.editProduct(updatedProduct, image, id);
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProductsList() {
        return ResponseEntity.ok(productService.getProductsList());
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Long id) {
        Product product = productService.getProduct(id);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) throws IOException {
        productService.deleteProduct(id);
        return ResponseEntity.ok("Mahsulot o‘chirildi");
    }
}