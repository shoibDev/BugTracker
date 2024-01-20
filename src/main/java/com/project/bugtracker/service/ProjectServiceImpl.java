package com.project.bugtracker.service;

import com.project.bugtracker.DTO.ProjectDTO;
import com.project.bugtracker.DTO.ProjectDTOMapper;
import com.project.bugtracker.Repository.ProjectRepository;
import com.project.bugtracker.model.Developer;
import com.project.bugtracker.model.Project;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService{

    private final ProjectRepository projectRepository;


    @Override
    public Project createProject(@RequestBody Project project) {
        return projectRepository.save(project);
    }


    @Override
    public List<Project> loadProjects() {
        return projectRepository.findAll();
    }

    @Override
    public Project findProjectById(Integer projectId) {
        return projectRepository.findById(projectId).get();
    }

    @Override
    public Project saveProject(Project project) {
        return projectRepository.save(project);
    }

    @Override
    public void deleteProjectById(Integer projectId) {

        // Then delete the Project entity
        projectRepository.deleteById(projectId);
    }

    @Override
    public ProjectDTO fromEntityToDTO(Project project) {
        return new ProjectDTOMapper().apply(project);
    }


//    @Override
//    public List<UserDTO> getUsersFromProject(Integer projectId) {
//        return findProjectById(projectId)
//                .getUserEntities()
//                .stream()
//                .map(userDTOMapper)
//                .collect(Collectors.toList());
//    }

    @Override
    public void deleteProject(Project project) {
        projectRepository.delete(project);
    }
}
