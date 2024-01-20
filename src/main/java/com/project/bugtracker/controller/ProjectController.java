package com.project.bugtracker.controller;

import com.project.bugtracker.DTO.DeveloperDTO;

import com.project.bugtracker.DTO.DeveloperDTOMapper;
import com.project.bugtracker.DTO.ProjectDTO;
import com.project.bugtracker.DTO.TicketDTO;
import com.project.bugtracker.model.Project;
import com.project.bugtracker.model.Developer;
import com.project.bugtracker.model.Ticket;
import com.project.bugtracker.service.ProjectService;
import com.project.bugtracker.service.TicketService;
import com.project.bugtracker.service.DeveloperService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/project")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class ProjectController {

    private final TicketService ticketService;
    private final ProjectService projectService;
    private final DeveloperService developerService;
    private final DeveloperDTOMapper developerDTOMapper;

    @PostMapping
    @PreAuthorize("hasAuthority('manager:write')")
    public ProjectDTO createProject(@RequestBody Project project, Principal principal) {

        Developer developer = developerService.getUserByEmail(principal.getName());
        developer.getProjects().add(project);

        project.getDevelopers().add(developer);
        projectService.createProject(project);

        return projectService.fromEntityToDTO(project);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('developer:read')")
    public List<ProjectDTO> developerProjects(Principal principal) {
        Developer developer = developerService.getUserByEmail(principal.getName());
        Set<Project> projects = developer.getProjects();

        // Convert Project entities to ProjectDTOs

        return projects.stream()
                .map(projectService::fromEntityToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{projectId}")
    @PreAuthorize("hasAuthority('developer:read')")
    public ProjectDTO getProjectById(@PathVariable("projectId") Integer projectId) {
        return projectService.fromEntityToDTO(projectService.findProjectById(projectId));
    }

// change this to a putmapping not a post mapping bruh
    // change path projectId/user/userId
    @PostMapping("/{projectId}/users/{userId}")
    @PreAuthorize("hasAuthority('manager:edit')")
    public ProjectDTO assignDeveloper(@PathVariable("userId") Integer userId,
                                   @PathVariable("projectId") Integer projectId) {
        Developer developer = developerService.findUserById(userId);
        Project project = projectService.findProjectById(projectId);

        developer.getProjects().add(project);
        project.getDevelopers().add(developer);

        return projectService.fromEntityToDTO(projectService.createProject(project));
    }

    @PostMapping("/{projectId}/users/{userId}/delete")
    @PreAuthorize("hasAuthority('manager:edit')")
    public ProjectDTO unassignedDeveloper(@PathVariable("userId") Integer userId,
                                       @PathVariable("projectId") Integer projectId) {
        Developer developer = developerService.findUserById(userId);
        Project project = projectService.findProjectById(projectId);

        developer.getProjects().remove(project);
        project.getDevelopers().remove(developer);

        return projectService.fromEntityToDTO(projectService.createProject(project));
    }

    @PutMapping("/{projectId}/edit")
    @PreAuthorize("hasAuthority('manager:edit')")
    public ProjectDTO editProject(@PathVariable("projectId")  Integer projectId,
                               @RequestBody Project updatedProject){
        Project Project = projectService.findProjectById(projectId);
        Project.setName(updatedProject.getName());
        Project.setDescription(updatedProject.getDescription());
        return projectService.fromEntityToDTO(projectService.saveProject(Project));
    }

    // edit it syuch that it doesnt print me aswell...
//    @GetMapping("/{projectId}/availableUsers")
//    @PreAuthorize("hasAuthority('manager:read')")
//    public List<DeveloperDTO> getAvailableUsers(@PathVariable("projectId") Integer projectId) {
//        List<DeveloperDTO> allUsers = developerService.retrieveAll();
//        Set<Developer> projectUsers = projectService.findProjectById(projectId).getDevelopers();
//
//        return allUsers.stream()
//                .filter(user -> !projectUsers.contains(user ))
//                .collect(Collectors.toList());
//    }

    @DeleteMapping("/{projectId}/delete")
    public void deleteProject(@PathVariable("projectId") Integer projectId) {
        Project project = projectService.findProjectById(projectId);

        Set<Developer> users = project.getDevelopers();
        for (Developer user : users) {
            user.getProjects().remove(project);
        }
        project.getDevelopers().clear();

        projectService.deleteProject(project);
    }

    @GetMapping("/{projectId}/tickets")
    @PreAuthorize("hasAuthority('developer:read')")
    public List<TicketDTO> getTickets(@PathVariable("projectId") Integer projectId, Principal principal) {
        Developer developer = developerService.getUserByEmail(principal.getName());
        Project project = projectService.findProjectById(projectId);
        Set<Ticket> projectTickets = project.getTickets();

        return projectTickets.stream()
                .filter(ticket -> ticket.getDevelopers().contains(developer))
                .map(ticketService::fromEntityToDTO)
                .collect(Collectors.toList());
    }

    @PutMapping("/{projectId}")
    @PreAuthorize("hasAuthority('developer:write')")
    public TicketDTO addTicket(@PathVariable("projectId") Integer projectId,
                               @RequestBody Ticket ticket,
                               Principal principal) {

        Developer developer = developerService.getUserByEmail(principal.getName());
        Project project = projectService.findProjectById(projectId);

        // Save the ticket entity first
        ticket.setProject(project);
        ticket.setAuthor(developer.getEmail());
        ticketService.saveTicket(ticket); // Assuming you have a service to manage tickets

        // Establish relationships
        developer.getTickets().add(ticket);
        ticket.getDevelopers().add(developer);
        project.getTickets().add(ticket);
        projectService.saveProject(project);

        return ticketService.fromEntityToDTO(ticket);
    }

    @GetMapping("/{projectId}/devs")
    @PreAuthorize("hasAuthority('developer:read')")
    public List<DeveloperDTO> getProjectDevs(@PathVariable("projectId") Integer projectId) {
        Project project = projectService.findProjectById(projectId);
        Set<Developer> projectDevelopers = project.getDevelopers();

        return projectDevelopers.stream()
                .map(developerDTOMapper) // Assuming you have a mapper
                .collect(Collectors.toList());
    }

    @GetMapping("/{projectId}/availableDevs")
    @PreAuthorize("hasAuthority('manager:read')")
    public List<DeveloperDTO> getAvailableDevelopers(@PathVariable("projectId") Integer projectId) {
        Project project = projectService.findProjectById(projectId);
        Set<Developer> projectDevelopers = project.getDevelopers();

        List<Developer> allDevelopers = developerService.retrieveAll(); // Assuming you have a method to retrieve all developers

        List<Developer> availableDevelopers = allDevelopers.stream()
                .filter(developer -> !projectDevelopers.contains(developer))
                .collect(Collectors.toList());

        return availableDevelopers.stream()
                .map(developerDTOMapper) // Assuming you have a mapper
                .collect(Collectors.toList());
    }


}
