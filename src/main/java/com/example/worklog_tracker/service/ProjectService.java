package com.example.worklog_tracker.service;

import com.example.worklog_tracker.model.Project;
import com.example.worklog_tracker.repository.ProjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    // Dependency Injection via Constructor (Best Practice di Spring Boot)
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    public Project findById(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Project tidak ditemukan dengan ID: " + id));
    }

    @Transactional
    public Project save(Project project) {
        return projectRepository.save(project);
    }

    @Transactional
    public void deleteById(Long id) {
        projectRepository.deleteById(id);
    }
}
