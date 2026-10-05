package com.example.worklog_tracker.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardStatsDto {

    private long totalProjects;
    private long totalTasks;
    private long completedTasks;
    private long inProgressTasks;
    private long blockedTasks;
    private long totalWorkMinutes;

    // Helper method untuk memformat total menit menjadi string jam & menit
    public String getFormattedTotalTime() {
        long hours = totalWorkMinutes / 60;
        long minutes = totalWorkMinutes % 60;
        if (hours == 0) {
            return minutes + " menit";
        }
        return hours + " jam " + minutes + " menit";
    }

    // Menghitung persentase penyelesaian tugas
    public int getCompletionPercentage() {
        if (totalTasks == 0) return 0;
        return (int) Math.round(((double) completedTasks / totalTasks) * 100);
    }
}
