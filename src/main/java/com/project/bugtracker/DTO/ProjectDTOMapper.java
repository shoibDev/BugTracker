package com.project.bugtracker.DTO;

import com.project.bugtracker.model.Developer;
import com.project.bugtracker.model.Project;
import org.springframework.stereotype.Component;

import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class ProjectDTOMapper implements Function<Project, ProjectDTO> {
    @Override
    public ProjectDTO apply(Project project) {
        return new ProjectDTO(
                project.getId(),
                project.getName(),
                project.getDescription(),
                project.getDateCreated(),
                project.getLastUpdated(),
                project.getDevelopers().stream()
                        .map(Developer::getId)  // Map Developer objects to their IDs
                        .collect(Collectors.toList())
        );
    }
}
