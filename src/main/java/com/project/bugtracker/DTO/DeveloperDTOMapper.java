package com.project.bugtracker.DTO;

import com.project.bugtracker.model.Developer;
import org.springframework.beans.factory.annotation.Autowired; // Import the annotation
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class DeveloperDTOMapper implements Function<Developer, DeveloperDTO> {

    private final ProjectDTOMapper projectDTOMapper; // Use final and inject through constructor

    @Autowired // Use @Autowired to inject the dependency
    public DeveloperDTOMapper(ProjectDTOMapper projectDTOMapper) {
        this.projectDTOMapper = projectDTOMapper;
    }

    public DeveloperDTOMapper() {

        projectDTOMapper = new ProjectDTOMapper();
    }

    @Override
    public DeveloperDTO apply(Developer developer) {
        Set<ProjectDTO> projectDTOs = developer.getProjects().stream()
                .map(projectDTOMapper::apply) // Use method reference
                .collect(Collectors.toSet());

        return new DeveloperDTO(
                developer.getId(),
                developer.getFirst_name(),
                developer.getLast_name(),
                developer.getEmail(),
                developer.getPhone_number(),
                developer.getRole(),
                projectDTOs
        );
    }
}
