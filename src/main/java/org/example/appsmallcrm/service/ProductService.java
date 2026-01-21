package org.example.appsmallcrm.service;

import lombok.RequiredArgsConstructor;
import org.example.appsmallcrm.entity.Product;
import org.example.appsmallcrm.repo.ProductRepo;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepo productRepo;
    private static final String UPLOAD_DIR = "uploads/";

    public Product getProduct(Long id) {
        return productRepo.findById(id).orElse(null);
    }

    public List<Product> getProductsList() {
        return productRepo.findAll();
    }

    public Integer getProductsCount() {
        int size = productRepo.findAll().size();
        System.out.println(size);
        return size;
    }

    public void deleteProduct(Long id) throws IOException {
        Optional<Product> optionalProduct = productRepo.findById(id);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            if (product.getImagePath() != null) {
                Path filePath = Paths.get(product.getImagePath().substring(1)); // "/" belgisini olib tashlash
                Files.deleteIfExists(filePath);
            }
            productRepo.deleteById(id);
        }
    }

    public Product createProduct(Product product, MultipartFile image) {
        if (image != null && !image.isEmpty()) {
            try {
                String originalFileName = StringUtils.cleanPath(image.getOriginalFilename());
                String extension = "";

                int i = originalFileName.lastIndexOf('.');
                if (i >= 0) {
                    extension = originalFileName.substring(i);
                }

                String fileName = UUID.randomUUID().toString() + extension;

                Path uploadDir = Paths.get("uploads");
                System.out.println("Uploads absolute path: " + uploadDir.toAbsolutePath());

                Files.createDirectories(uploadDir);

                Path filePath = uploadDir.resolve(fileName);

                try (InputStream inputStream = image.getInputStream()) {
                    Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
                }

                System.out.println("File size: " + image.getSize());
                System.out.println("Saved file: " + filePath.toAbsolutePath());

                product.setImagePath("/uploads/" + fileName);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return productRepo.save(product);
    }


    public ResponseEntity<?> editProduct(Product updatedProduct, MultipartFile image, Long id) throws IOException {
        Optional<Product> optionalProduct = productRepo.findById(id);
        if (optionalProduct.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Product existingProduct = optionalProduct.get();
        existingProduct.setName(updatedProduct.getName());
        existingProduct.setPrice(updatedProduct.getPrice());
        existingProduct.setStock(updatedProduct.getStock());
        existingProduct.setStatus(updatedProduct.getStatus());

        // Agar yangi rasm yuklansa, uni saqlash
        if (image != null && !image.isEmpty()) {
            String fileName = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();
            Path filePath = Paths.get(UPLOAD_DIR + fileName);

            // Yuklash papkasini yaratish
            Files.createDirectories(filePath.getParent());

            // Faylni serverga saqlash
            Files.write(filePath, image.getBytes());

            // Eski rasmni o'chirish (agar mavjud bo'lsa)
            if (existingProduct.getImagePath() != null) {
                Path oldFilePath = Paths.get(existingProduct.getImagePath().substring(1));
                Files.deleteIfExists(oldFilePath);
            }

            // Yangi rasm manzilini qo'shish
            existingProduct.setImagePath("/" + UPLOAD_DIR + fileName);
        }

        // Yangilangan mahsulotni saqlash
        productRepo.saveAndFlush(existingProduct);
        return ResponseEntity.ok("Mahsulot muvaffaqiyatli yangilandi");
    }
}