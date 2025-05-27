package org.example.appsmallcrm.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.appsmallcrm.entity.Order;
import org.example.appsmallcrm.entity.Product;
import org.example.appsmallcrm.repo.OrderRepo;
import org.example.appsmallcrm.repo.ProductRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepo orderRepository;
    private final ProductRepo productRepository;


    public Order createOrder(Long productId, String customer, int quantity) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new RuntimeException("Product not found"));

        if (product.getStock() < quantity) {
            throw new RuntimeException("Not enough stock for product: " + product.getName());
        }

        product.setStock(product.getStock() - quantity);
        productRepository.save(product);

        Order order = Order.builder()
            .product(product)
            .customer(customer)
            .date(LocalDate.now())
            .amount(product.getPrice() * quantity)
            .status("Created")
            .build();

        return orderRepository.save(order);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}
