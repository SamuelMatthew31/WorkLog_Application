package com.example.worklog_tracker.controller;

import com.example.worklog_tracker.model.Project;
import com.example.worklog_tracker.model.Task;
import com.example.worklog_tracker.model.TaskPriority;
import com.example.worklog_tracker.model.TaskStatus;
import com.example.worklog_tracker.service.ProjectService;
import com.example.worklog_tracker.service.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class TaskController {

    private final TaskService taskService;
    private final ProjectService projectService;

    public TaskController(TaskService taskService, ProjectService projectService) {
        this.taskService = taskService;
        this.projectService = projectService;
    }

    @GetMapping("/projects/{projectId}/tasks/new")
    public String showCreateTaskForm(@PathVariable Long projectId, Model model) {
        Project project = projectService.findById(projectId);
        Task task = new Task();
        task.setProject(project);

        model.addAttribute("task", task);
        model.addAttribute("projectId", projectId);
        model.addAttribute("statuses", TaskStatus.values());
        model.addAttribute("priorities", TaskPriority.values());
        return "tasks/form";
    }

    @PostMapping("/projects/{projectId}/tasks")
    public String saveTask(@PathVariable Long projectId, @ModelAttribute Task task) {
        Project project = projectService.findById(projectId);
        task.setProject(project);
        taskService.save(task);
        return "redirect:/projects/" + projectId;
    }

    @GetMapping("/projects/{projectId}/tasks/{taskId}/edit")
    public String showEditTaskForm(@PathVariable Long projectId, @PathVariable Long taskId, Model model) {
        Task task = taskService.findById(taskId);
        model.addAttribute("task", task);
        model.addAttribute("projectId", projectId);
        model.addAttribute("statuses", TaskStatus.values());
        model.addAttribute("priorities", TaskPriority.values());
        return "tasks/form";
    }

    @PostMapping("/projects/{projectId}/tasks/{taskId}/edit")
    public String updateTask(@PathVariable Long projectId,
                             @PathVariable Long taskId,
                             @ModelAttribute Task task) {
        Project project = projectService.findById(projectId);
        task.setId(taskId);
        task.setProject(project);
        taskService.save(task);
        return "redirect:/projects/" + projectId;
    }

    // Handler Endpoint Search & Filter Tugas
    @GetMapping("/tasks/search")
    public String searchTasks(@RequestParam(required = false) String keyword,
                              @RequestParam(required = false) TaskStatus status,
                              @RequestParam(required = false) TaskPriority priority,
                              Model model) {

        List<Task> tasks = taskService.searchTasks(keyword, status, priority);

        model.addAttribute("tasks", tasks);
        model.addAttribute("keyword", keyword);
        model.addAttribute("selectedStatus", status);
        model.addAttribute("selectedPriority", priority);
        model.addAttribute("statuses", TaskStatus.values());
        model.addAttribute("priorities", TaskPriority.values());

        return "tasks/search";
    }
}
