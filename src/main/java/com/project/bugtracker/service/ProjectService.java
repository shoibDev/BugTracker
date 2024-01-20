package com.project.bugtracker.service;

import com.project.bugtracker.DTO.ProjectDTO;
import com.project.bugtracker.model.Developer;
import com.project.bugtracker.model.Project;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface ProjectService {

    Project createProject(@RequestBody Project project);


    List<Project> loadProjects();

    Project findProjectById(Integer projectId);

    Project saveProject(Project project);

    void deleteProjectById(Integer projectId);

    ProjectDTO fromEntityToDTO(Project project);

    void deleteProject(Project project);

}
