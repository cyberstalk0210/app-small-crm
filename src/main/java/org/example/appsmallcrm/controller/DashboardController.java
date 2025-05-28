package org.example.appsmallcrm.controller;

import lombok.RequiredArgsConstructor;
import org.example.appsmallcrm.entity.Activity;
import org.example.appsmallcrm.entity.Customer;
import org.example.appsmallcrm.entity.DashboardStats;
import org.example.appsmallcrm.service.DashboardStatsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
//@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardStatsService dashboardStatsService;


    @GetMapping("/dashboard/stats")
    public ResponseEntity<DashboardStats> getDashboardStats() {
        try {
            return ResponseEntity.ok(dashboardStatsService.getStats());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/sales/bar")
    public ResponseEntity<List<Map<String, Object>>> getBarData() {
        try {
            return ResponseEntity.ok(dashboardStatsService.getBarData());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/sales/line")
    public ResponseEntity<List<Map<String, Object>>> getLineData() {
        try {
            return ResponseEntity.ok(dashboardStatsService.getLineData());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/sales/pie")
    public ResponseEntity<List<Map<String, Object>>> getPieData() {
        try {
            return ResponseEntity.ok(dashboardStatsService.getPieData());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/customers")
    public ResponseEntity<List<Customer>> getCustomers() {
        try {
            return ResponseEntity.ok(dashboardStatsService.getCustomers());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/activities")
    public ResponseEntity<List<Activity>> getActivities() {
        try {
            return ResponseEntity.ok(dashboardStatsService.getActivities());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}