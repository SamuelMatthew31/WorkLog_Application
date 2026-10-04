package com.example.worklog_tracker.controller;

import com.example.worklog_tracker.model.WorkLog;
import com.example.worklog_tracker.service.TaskService;
import com.example.worklog_tracker.service.WorkLogService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/worklogs")
public class WorkLogController {

    private final WorkLogService workLogService;
    private final TaskService taskService;

    public WorkLogController(WorkLogService workLogService, TaskService taskService) {
        this.workLogService = workLogService;
        this.taskService = taskService;
    }

    // Menambahkan catatan durasi kerja baru pada suatu tugas
    @PostMapping("/add")
    public String addWorkLog(@RequestParam("taskId") Long taskId,
                             @Valid @ModelAttribute("newWorkLog") WorkLog workLog) {
        workLog.setTask(taskService.findById(taskId));
        workLogService.save(workLog);
        return "redirect:/tasks/" + taskId;
    }

    // Menghapus catatan durasi kerja
    @PostMapping("/{id}/delete")
    public String deleteWorkLog(@PathVariable("id") Long id, @RequestParam("taskId") Long taskId) {
        workLogService.deleteById(id);
        return "redirect:/tasks/" + taskId;
    }
}
