package com.example.worklog_tracker.repository;

import com.example.worklog_tracker.model.Task;
import com.example.worklog_tracker.model.TaskPriority;
import com.example.worklog_tracker.model.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByProjectId(Long projectId);

    List<Task> findByStatus(TaskStatus status);

    long countByStatus(TaskStatus status);

    // Query JPQL Dinamis untuk Pencarian & Filtering Tugas
    @Query("SELECT t FROM Task t WHERE " +
           "(:keyword IS NULL OR :keyword = '' OR LOWER(t.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(t.description) LIKE LOWER(CONCAT('%', :keyword, '%'))) AND " +
           "(:status IS NULL OR t.status = :status) AND " +
           "(:priority IS NULL OR t.priority = :priority)")
    List<Task> searchTasks(@Param("keyword") String keyword,
                           @Param("status") TaskStatus status,
                           @Param("priority") TaskPriority priority);
}
