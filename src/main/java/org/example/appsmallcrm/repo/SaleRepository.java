package org.example.appsmallcrm.repo;

import org.example.appsmallcrm.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query("SELECT COUNT(s) FROM Sale s")
    int countAllSales();

    @Query("SELECT SUM(s.revenue) FROM Sale s WHERE MONTH(s.soldDate) = MONTH(CURRENT_DATE) AND YEAR(s.soldDate) = YEAR(CURRENT_DATE)")
    Double sumRevenueCurrentMonth();

    @Query("SELECT SUM(s.revenue) FROM Sale s WHERE MONTH(s.soldDate) = MONTH(CURRENT_DATE) - 1 AND YEAR(s.soldDate) = YEAR(CURRENT_DATE)")
    Double sumRevenueLastMonth();

    @Query("SELECT COUNT(s) FROM Sale s WHERE MONTH(s.soldDate) = MONTH(CURRENT_DATE) AND YEAR(s.soldDate) = YEAR(CURRENT_DATE)")
    int countSoldCurrentMonth();

    @Query("SELECT COUNT(s) FROM Sale s WHERE MONTH(s.soldDate) = MONTH(CURRENT_DATE) - 1 AND YEAR(s.soldDate) = YEAR(CURRENT_DATE)")
    int countSoldLastMonth();

    @Query("SELECT AVG(s.quantity) FROM Sale s")
    Double calculateAvgMonthlySales();

    @Query("SELECT AVG(s.quantity) FROM Sale s WHERE MONTH(s.soldDate) = MONTH(CURRENT_DATE) - 1 AND YEAR(s.soldDate) = YEAR(CURRENT_DATE)")
    Double calculateAvgSalesLastMonth();
}
