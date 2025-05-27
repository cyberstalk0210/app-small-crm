package org.example.appsmallcrm.service;

import lombok.RequiredArgsConstructor;
import org.example.appsmallcrm.entity.DashboardStats;
import org.example.appsmallcrm.repo.ProductRepo;
import org.example.appsmallcrm.repo.SaleRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardStatsService{

    private final SaleRepository saleRepository;
    private final ProductRepo productRepo;

    public DashboardStats getStats() {
        DashboardStats stats = new DashboardStats();

        int totalProduct = (int) productRepo.count();

        double revenueThisMonth = saleRepository.sumRevenueCurrentMonth();

        double revenueLastMonth = saleRepository.sumRevenueLastMonth();

        int soldThisMonth = saleRepository.countSoldCurrentMonth();

        int soldLastMonth = saleRepository.countSoldLastMonth();

        double avgMonthlySales = saleRepository.calculateAvgMonthlySales();

        double avgSalesLastMonth = saleRepository.calculateAvgSalesLastMonth();

        stats.setTotalProduct(totalProduct);
        stats.setProductRevenue(revenueThisMonth);
        stats.setProductSold(soldThisMonth);
        stats.setAvgMonthlySales(avgMonthlySales);

        stats.setRevenueChange(calcPercentageChange(revenueLastMonth, revenueThisMonth));
        stats.setSoldChange(calcPercentageChange(soldLastMonth, soldThisMonth));
        stats.setAvgSalesChange(calcPercentageChange(avgSalesLastMonth, avgMonthlySales));

        return stats;
    }

    private double calcPercentageChange(double last, double current) {
        if (last == 0) return 100.0;
        return ((current - last) / last) * 100.0;
    }

}
