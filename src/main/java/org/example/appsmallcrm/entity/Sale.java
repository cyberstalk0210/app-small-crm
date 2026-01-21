package org.example.appsmallcrm.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private LocalDate soldDate;
    private Double revenue;
    private Integer quantity;

    public Sale(Product product, LocalDate soldDate, Double revenue, Integer quantity) {
        this.product = product;
        this.soldDate = soldDate;
        this.revenue = revenue;
        this.quantity = quantity;
    }
}
