package com.example.worklog_tracker.service;

import com.example.worklog_tracker.model.Task;
import com.example.worklog_tracker.model.TaskPriority;
import com.example.worklog_tracker.model.TaskStatus;
import com.example.worklog_tracker.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    public Task findById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task tidak ditemukan dengan ID: " + id));
    }

    public Task save(Task task) {
        return taskRepository.save(task);
    }

    public void deleteById(Long id) {
        taskRepository.deleteById(id);
    }

    // Method Pencarian & Filtering
    public List<Task> searchTasks(String keyword, TaskStatus status, TaskPriority priority) {
        return taskRepository.searchTasks(keyword, status, priority);
    }

    // Tambahkan method ini di dalam class TaskService
    public Task updateTaskStatus(Long taskId, TaskStatus newStatus) {
        Task task = findById(taskId);
        task.setStatus(newStatus);
        return taskRepository.save(task);
    }
}
