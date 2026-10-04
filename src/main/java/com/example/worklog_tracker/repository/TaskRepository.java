package com.example.worklog_tracker.repository;

import com.example.worklog_tracker.model.Task;
import com.example.worklog_tracker.model.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    // Mencari semua tugas berdasarkan ID project-nya
    List<Task> findByProjectId(Long projectId);

    // Mencari tugas berdasarkan statusnya (contoh: TODO, IN_PROGRESS)
    List<Task> findByStatus(TaskStatus status);
}
