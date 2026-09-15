package com.krishna.ems.controller;

import com.krishna.ems.dto.dashboard.AdminDashboardResponse;
import com.krishna.ems.dto.dashboard.EmployeeDashboardResponse;
import com.krishna.ems.service.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;


    public DashboardController(
            DashboardService dashboardService) {

        this.dashboardService = dashboardService;
    }


    // =========================================================
    // EMPLOYEE DASHBOARD
    // =========================================================

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<EmployeeDashboardResponse>
    getEmployeeDashboard(
            @PathVariable Long employeeId) {

        return ResponseEntity.ok(
                dashboardService.getEmployeeDashboard(employeeId)
        );
    }


    // =========================================================
    // ADMIN DASHBOARD
    // =========================================================

    @GetMapping("/admin")
    public ResponseEntity<AdminDashboardResponse>
    getAdminDashboard() {

        return ResponseEntity.ok(
                dashboardService.getAdminDashboard()
        );
    }
}