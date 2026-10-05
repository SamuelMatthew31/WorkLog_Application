package com.example.worklog_tracker.repository;

import com.example.worklog_tracker.model.WorkLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkLogRepository extends JpaRepository<WorkLog, Long> {

    List<WorkLog> findByTaskIdOrderByLoggedAtDesc(Long taskId);

    @Query("SELECT COALESCE(SUM(w.durationMinutes), 0) FROM WorkLog w WHERE w.task.id = :taskId")
    Integer getTotalDurationByTaskId(@Param("taskId") Long taskId);

    // Query agregat global untuk menghitung total seluruh durasi kerja dalam aplikasi
    @Query("SELECT COALESCE(SUM(w.durationMinutes), 0) FROM WorkLog w")
    Integer getTotalGlobalWorkMinutes();
}
