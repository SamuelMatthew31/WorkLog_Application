package com.example.worklog_tracker.controller;

import com.example.worklog_tracker.model.Project;
import com.example.worklog_tracker.service.ProjectService;
import com.example.worklog_tracker.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService projectService;
    private final TaskService taskService;

    public ProjectController(ProjectService projectService, TaskService taskService) {
        this.projectService = projectService;
        this.taskService = taskService;
    }

    // Menampilkan daftar semua proyek
    @GetMapping
    public String listProjects(Model model) {
        model.addAttribute("projects", projectService.findAll());
        return "projects/list";
    }

    // Menampilkan form pembuatan proyek baru
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("project", new Project());
        return "projects/form";
    }

    // Memproses simpan proyek baru
    @PostMapping
    public String saveProject(@Valid @ModelAttribute("project") Project project,
                              BindingResult bindingResult,
                              Model model) {
        if (bindingResult.hasErrors()) {
            return "projects/form";
        }
        projectService.save(project);
        return "redirect:/projects";
    }

    // Menampilkan detail proyek beserta daftar tugas di dalamnya
    @GetMapping("/{id}")
    public String viewProjectDetail(@PathVariable("id") Long id, Model model) {
        Project project = projectService.findById(id);
        model.addAttribute("project", project);
        model.addAttribute("tasks", taskService.findByProjectId(id));
        return "projects/detail";
    }

    // Menghapus proyek
    @PostMapping("/{id}/delete")
    public String deleteProject(@PathVariable("id") Long id) {
        projectService.deleteById(id);
        return "redirect:/projects";
    }
}
