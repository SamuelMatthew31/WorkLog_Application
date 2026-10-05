package com.example.worklog_tracker.controller;

import com.example.worklog_tracker.model.Project;
import com.example.worklog_tracker.model.Task;
import com.example.worklog_tracker.model.TaskStatus;
import com.example.worklog_tracker.service.ProjectService;
import com.example.worklog_tracker.service.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService projectService;
    private final TaskService taskService;

    public ProjectController(ProjectService projectService, TaskService taskService) {
        this.projectService = projectService;
        this.taskService = taskService;
    }

    @GetMapping
    public String listProjects(Model model) {
        model.addAttribute("projects", projectService.findAll());
        return "projects/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("project", new Project());
        return "projects/form";
    }

    @PostMapping
    public String saveProject(@ModelAttribute Project project) {
        projectService.save(project);
        return "redirect:/projects";
    }

    @GetMapping("/{id}")
    public String showDetail(@PathVariable Long id, Model model) {
        Project project = projectService.findById(id);
        model.addAttribute("project", project);
        return "projects/detail";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Project project = projectService.findById(id);
        model.addAttribute("project", project);
        return "projects/form";
    }

    @PostMapping("/{id}/edit")
    public String updateProject(@PathVariable Long id, @ModelAttribute Project project) {
        project.setId(id);
        projectService.save(project);
        return "redirect:/projects/" + id;
    }

    @PostMapping("/{id}/delete")
    public String deleteProject(@PathVariable Long id) {
        projectService.deleteById(id);
        return "redirect:/projects";
    }

    // Handler Menampilkan Papan Kanban Proyek
    @GetMapping("/{id}/kanban")
    public String showKanbanBoard(@PathVariable Long id, Model model) {
        Project project = projectService.findById(id);

        // Pengelompokkan Tugas berdasarkan Status menggunakan Java Stream
        Map<TaskStatus, List<Task>> tasksByStatus = project.getTasks().stream()
                .collect(Collectors.groupingBy(Task::getStatus));

        model.addAttribute("project", project);
        model.addAttribute("kanbanMap", tasksByStatus);
        model.addAttribute("allStatuses", TaskStatus.values());
        return "projects/kanban";
    }

    // Handler Pembaruan Status Cepat dari Kanban
    @PostMapping("/{projectId}/tasks/{taskId}/status")
    public String updateStatusQuick(@PathVariable Long projectId,
                                    @PathVariable Long taskId,
                                    @RequestParam TaskStatus newStatus) {
        taskService.updateTaskStatus(taskId, newStatus);
        return "redirect:/projects/" + projectId + "/kanban";
    }
}
