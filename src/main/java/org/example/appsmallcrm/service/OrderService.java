package org.example.appsmallcrm.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.appsmallcrm.dto.OrderRequestDTO;
import org.example.appsmallcrm.entity.Order;
import org.example.appsmallcrm.entity.Product;
import org.example.appsmallcrm.repo.OrderRepo;
import org.example.appsmallcrm.repo.ProductRepo;
import org.example.appsmallcrm.repo.SaleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepo orderRepository;
    private final ProductRepo productRepository;
    private final SaleRepository saleRepository;


        @Transactional
        public Order createOrder(OrderRequestDTO orderRequest) {
            // Validate product
            Product product = productRepository.findById(orderRequest.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found with id: " + orderRequest.getProductId()));

            // Check stock
            if (product.getStock() < orderRequest.getQuantity()) {
                throw new RuntimeException("Insufficient stock for product: " + product.getName());
            }

            // Calculate amount
            Double amount = product.getPrice() * orderRequest.getQuantity();

            // Create order
            Order order = Order.builder()
                    .product(product)
                    .customer(orderRequest.getCustomer())
                    .date(LocalDate.now()) // Current date in +05 time zone
                    .amount(amount)
                    .status("Pending") // Default status
                    .build();

            // Update product stock
            product.setStock(product.getStock() - orderRequest.getQuantity());
            productRepository.save(product);
            return orderRepository.save(order);
        }
        public List<Order> getAllOrders() {
            return orderRepository.findAll();
        }
}
