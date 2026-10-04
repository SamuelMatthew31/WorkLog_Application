package com.example.worklog_tracker.service;

import com.example.worklog_tracker.model.WorkLog;
import com.example.worklog_tracker.repository.WorkLogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WorkLogService {

    private final WorkLogRepository workLogRepository;

    public WorkLogService(WorkLogRepository workLogRepository) {
        this.workLogRepository = workLogRepository;
    }

    public List<WorkLog> findByTaskId(Long taskId) {
        return workLogRepository.findByTaskIdOrderByLoggedAtDesc(taskId);
    }

    @Transactional
    public WorkLog save(WorkLog workLog) {
        return workLogRepository.save(workLog);
    }

    public Integer getTotalDurationForTask(Long taskId) {
        return workLogRepository.getTotalDurationByTaskId(taskId);
    }

    @Transactional
    public void deleteById(Long id) {
        workLogRepository.deleteById(id);
    }
}
