package com.example.worklog_tracker.service;

import com.example.worklog_tracker.dto.DashboardStatsDto;
import com.example.worklog_tracker.model.TaskStatus;
import com.example.worklog_tracker.repository.ProjectRepository;
import com.example.worklog_tracker.repository.TaskRepository;
import com.example.worklog_tracker.repository.WorkLogRepository;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;
    private final WorkLogRepository workLogRepository;

    public DashboardService(ProjectRepository projectRepository,
                            TaskRepository taskRepository,
                            WorkLogRepository workLogRepository) {
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
        this.workLogRepository = workLogRepository;
    }

    public DashboardStatsDto getDashboardStats() {
        long totalProjects = projectRepository.count();
        long totalTasks = taskRepository.count();
        long completedTasks = taskRepository.countByStatus(TaskStatus.DONE);
        long inProgressTasks = taskRepository.countByStatus(TaskStatus.IN_PROGRESS);
        long blockedTasks = taskRepository.countByStatus(TaskStatus.BLOCKED);

        Integer totalMinutes = workLogRepository.getTotalGlobalWorkMinutes();
        long totalWorkMinutes = (totalMinutes != null) ? totalMinutes : 0;

        return DashboardStatsDto.builder()
                .totalProjects(totalProjects)
                .totalTasks(totalTasks)
                .completedTasks(completedTasks)
                .inProgressTasks(inProgressTasks)
                .blockedTasks(blockedTasks)
                .totalWorkMinutes(totalWorkMinutes)
                .build();
    }
}
