package com.example.worklog_tracker.controller;

import com.example.worklog_tracker.model.Task;
import com.example.worklog_tracker.model.TaskPriority;
import com.example.worklog_tracker.model.TaskStatus;
import com.example.worklog_tracker.model.WorkLog;
import com.example.worklog_tracker.service.ProjectService;
import com.example.worklog_tracker.service.TaskService;
import com.example.worklog_tracker.service.WorkLogService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;
    private final ProjectService projectService;
    private final WorkLogService workLogService;

    public TaskController(TaskService taskService, ProjectService projectService, WorkLogService workLogService) {
        this.taskService = taskService;
        this.projectService = projectService;
        this.workLogService = workLogService;
    }

    // Menampilkan form pembuatan tugas baru
    @GetMapping("/new")
    public String showCreateForm(@RequestParam(value = "projectId", required = false) Long projectId, Model model) {
        Task task = new Task();
        if (projectId != null) {
            task.setProject(projectService.findById(projectId));
        }

        model.addAttribute("task", task);
        model.addAttribute("projects", projectService.findAll());
        model.addAttribute("statuses", TaskStatus.values());
        model.addAttribute("priorities", TaskPriority.values());
        return "tasks/form";
    }

    // Memproses simpan tugas baru
    @PostMapping
    public String saveTask(@Valid @ModelAttribute("task") Task task,
                           BindingResult bindingResult,
                           Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("projects", projectService.findAll());
            model.addAttribute("statuses", TaskStatus.values());
            model.addAttribute("priorities", TaskPriority.values());
            return "tasks/form";
        }
        Task savedTask = taskService.save(task);
        return "redirect:/projects/" + savedTask.getProject().getId();
    }

    // Menampilkan detail tugas beserta log waktu pengerjaannya
    @GetMapping("/{id}")
    public String viewTaskDetail(@PathVariable("id") Long id, Model model) {
        Task task = taskService.findById(id);
        model.addAttribute("task", task);
        model.addAttribute("workLogs", workLogService.findByTaskId(id));
        model.addAttribute("totalDuration", workLogService.getTotalDurationForTask(id));
        model.addAttribute("newWorkLog", new WorkLog());
        model.addAttribute("statuses", TaskStatus.values());
        return "tasks/detail";
    }

    // Mengubah status tugas secara cepat (pilihan opsi drop-down)
    @PostMapping("/{id}/status")
    public String updateStatus(@PathVariable("id") Long id, @RequestParam("status") TaskStatus status) {
        taskService.updateStatus(id, status);
        return "redirect:/tasks/" + id;
    }

    // Menghapus tugas
    @PostMapping("/{id}/delete")
    public String deleteTask(@PathVariable("id") Long id) {
        Task task = taskService.findById(id);
        Long projectId = task.getProject().getId();
        taskService.deleteById(id);
        return "redirect:/projects/" + projectId;
    }
}
