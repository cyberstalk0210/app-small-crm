package org.example.appsmallcrm.service;

import lombok.RequiredArgsConstructor;
import org.example.appsmallcrm.entity.Activity;
import org.example.appsmallcrm.entity.Customer;
import org.example.appsmallcrm.entity.DashboardStats;
import org.example.appsmallcrm.repo.ActivityRepo;
import org.example.appsmallcrm.repo.CustomerRepo;
import org.example.appsmallcrm.repo.ProductRepo;
import org.example.appsmallcrm.repo.SaleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardStatsService {
    private final SaleRepository saleRepository;
    private final ProductRepo productRepository;
    private final CustomerRepo customerRepository;
    private final ActivityRepo activityRepository;

    public DashboardStats getStats() {
        DashboardStats stats = new DashboardStats();
        stats.setProductSold(saleRepository.countAllSales());
        stats.setTotalProduct((int) productRepository.count());
        stats.setProductRevenue(saleRepository.sumRevenueCurrentMonth() != null ? saleRepository.sumRevenueCurrentMonth() : 0.0);
        stats.setProductSold(saleRepository.countSoldCurrentMonth() != null ? saleRepository.countSoldCurrentMonth().intValue() : 0);
        stats.setAvgMonthlySales(saleRepository.calculateAvgMonthlySales() != null ? saleRepository.calculateAvgMonthlySales() : 0.0);
        stats.setRevenueChange(calcPercentageChange(saleRepository.sumRevenueLastMonth(), saleRepository.sumRevenueCurrentMonth()));
        stats.setSoldChange(calcPercentageChange(saleRepository.countSoldLastMonth(), saleRepository.countSoldCurrentMonth()));
        stats.setAvgSalesChange(calcPercentageChange(saleRepository.calculateAvgSalesLastMonth(), saleRepository.calculateAvgMonthlySales()));
        return stats;
    }

    private double calcPercentageChange(Double last, Double current) {
        if (last == null || last == 0) return 100.0;
        if (current == null) return - 100.0;
        return ((current - last) / last) * 100.0;
    }

    public List<Map<String, Object>> getBarData() {
        List<Object[]> monthlySales = saleRepository.findMonthlySales();
        return monthlySales.stream()
                .map(data -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("month", getMonthName((Integer) data[0]));
                    map.put("total", (Double) data[1]);
                    return map;
                })
                .collect(Collectors.toList());
    }

    public List<Map<String, Object>> getLineData() {
        LocalDate startDate = LocalDate.now().minusDays(30);
        List<Object[]> dailySales = saleRepository.findDailySales(startDate);
        return dailySales.stream()
                .map(data -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("date", ((LocalDate) data[0]).toString());
                    map.put("total", (Double) data[1]);
                    return map;
                })
                .collect(Collectors.toList());
    }

    public List<Map<String, Object>> getPieData() {
        List<Object[]> salesByProduct = saleRepository.findSalesByProduct();
        return salesByProduct.stream()
                .map(data -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("product", (String) data[0]);
                    map.put("total", (Double) data[1]);
                    return map;
                })
                .collect(Collectors.toList());
    }

    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    public List<Activity> getActivities() {
        return activityRepository.findAll();
    }

    private String getMonthName(int month) {
        return Month.of(month).getDisplayName(TextStyle.FULL, Locale.ENGLISH);
    }
}