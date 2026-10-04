package com.example.worklog_tracker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index() {
        // Mengarahkan root URL (http://localhost:8080/) langsung ke halaman daftar proyek
        return "redirect:/projects";
    }
}
