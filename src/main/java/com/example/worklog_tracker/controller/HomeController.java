package com.example.worklog_tracker.controller;

import com.example.worklog_tracker.service.DashboardService;
import com.example.worklog_tracker.service.ProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final DashboardService dashboardService;
    private final ProjectService projectService;

    public HomeController(DashboardService dashboardService, ProjectService projectService) {
        this.dashboardService = dashboardService;
        this.projectService = projectService;
    }

    @GetMapping("/")
    public String dashboard(Model model) {
        model.addAttribute("stats", dashboardService.getDashboardStats());
        model.addAttribute("projects", projectService.findAll());
        return "dashboard";
    }
}
