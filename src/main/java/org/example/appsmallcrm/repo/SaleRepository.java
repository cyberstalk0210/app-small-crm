package org.example.appsmallcrm.repo;

import org.example.appsmallcrm.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query("SELECT COUNT(s) FROM Sale s")
    int countAllSales();

    @Query("SELECT SUM(s.revenue) FROM Sale s WHERE MONTH(s.soldDate) = MONTH(CURRENT_DATE) AND YEAR(s.soldDate) = YEAR(CURRENT_DATE)")
    Double sumRevenueCurrentMonth();

    @Query("SELECT SUM(s.revenue) FROM Sale s WHERE MONTH(s.soldDate) = MONTH(CURRENT_DATE) - 1 AND YEAR(s.soldDate) = YEAR(CURRENT_DATE)")
    Double sumRevenueLastMonth();

    @Query("SELECT COUNT(s) FROM Sale s WHERE MONTH(s.soldDate) = MONTH(CURRENT_DATE) AND YEAR(s.soldDate) = YEAR(CURRENT_DATE)")
    Double countSoldCurrentMonth(); // Changed to Long

    @Query("SELECT COUNT(s) FROM Sale s WHERE MONTH(s.soldDate) = MONTH(CURRENT_DATE) - 1 AND YEAR(s.soldDate) = YEAR(CURRENT_DATE)")
    Double countSoldLastMonth(); // Changed to Long

    @Query("SELECT AVG(s.quantity) FROM Sale s")
    Double calculateAvgMonthlySales();

    @Query("SELECT AVG(s.quantity) FROM Sale s WHERE MONTH(s.soldDate) = MONTH(CURRENT_DATE) - 1 AND YEAR(s.soldDate) = YEAR(CURRENT_DATE)")
    Double calculateAvgSalesLastMonth();

    @Query("SELECT MONTH(s.soldDate) as month, SUM(s.revenue) as total FROM Sale s WHERE YEAR(s.soldDate) = YEAR(CURRENT_DATE) GROUP BY MONTH(s.soldDate)")
    List<Object[]> findMonthlySales();

    @Query("SELECT s.soldDate as date, SUM(s.revenue) as total FROM Sale s WHERE s.soldDate >= :startDate GROUP BY s.soldDate")
    List<Object[]> findDailySales(@Param("startDate") LocalDate startDate);

    @Query("SELECT p.name, SUM(s.revenue) as total FROM Sale s JOIN Product p ON s.product.id = p.id GROUP BY p.name")
    List<Object[]> findSalesByProduct();
}