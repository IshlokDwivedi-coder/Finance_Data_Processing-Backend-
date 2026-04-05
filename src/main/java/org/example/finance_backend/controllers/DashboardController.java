package org.example.finance_backend.controllers;

import org.example.finance_backend.service.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    // Viewer ,Analyst, Admin all can see the dashboard summary
    @GetMapping("/summary")
    @PreAuthorize("hasAnyRole('VIEWER','ANALYST','ADMIN')")
    public ResponseEntity<Map<String,Object>> viewDashboard(){
        Map<String ,Object> summary=dashboardService.getSummary();
        if(summary==null || summary.isEmpty()){
            return ResponseEntity.noContent().build(); // 204
        }
        return ResponseEntity.ok(summary);
    }
}
