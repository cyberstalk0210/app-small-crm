package org.example.appsmallcrm.service;

import lombok.RequiredArgsConstructor;
import org.example.appsmallcrm.entity.Product;
import org.example.appsmallcrm.repo.ProductRepo;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService{

    private final ProductRepo productRepo;

    public Product getProduct(Long id) {
        return productRepo.findById(id).map(product ->
                productRepo.getReferenceById(product.getId()))
                    .orElse(null);
    }

    public Product createProduct(Product product) {
         return productRepo.save(product);
    }

    public List<Product> getProductsList(){
        return productRepo.findAll();
    }

    public Integer getProductsCount() {
        int size = productRepo.findAll().size();
        System.out.println(size);
        return size;
    }
    public void deleteProduct(Long id) {
        productRepo.deleteById(id);
    }

    public ResponseEntity<?> editProduct(Product updatedProduct, Long id) {
        Optional<Product> optionalProduct = productRepo.findById(id);
        if (optionalProduct.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Product existingProduct = optionalProduct.get();
        existingProduct.setName(updatedProduct.getName());
        existingProduct.setPrice(updatedProduct.getPrice());
        existingProduct.setStock(updatedProduct.getStock());
        existingProduct.setStatus(updatedProduct.getStatus());

        productRepo.saveAndFlush(existingProduct);
        return ResponseEntity.ok("Product updated successfully");
    }
}
