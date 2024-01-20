package com.project.bugtracker.controller;

import com.project.bugtracker.DTO.TicketDTO;
import com.project.bugtracker.DTO.TicketDTOMapper;
import com.project.bugtracker.model.Project;
import com.project.bugtracker.model.Ticket;
import com.project.bugtracker.model.Developer;
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

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/ticket")
public class TicketController {

    private final DeveloperService developerService;
    private final TicketDTOMapper ticketDTOMapper;
    private final ProjectService projectService;
    private final TicketService ticketService;

    @GetMapping
    @PreAuthorize("hasAuthority('developer:read')")
    List<TicketDTO> retrieveAllTickets(Principal principal) {
        Developer developer = developerService.getUserByEmail(principal.getName());
        Set<Ticket> tickets = developer.getTickets();

        return tickets.stream()
                .map(ticketService::fromEntityToDTO)
                .collect(Collectors.toList());
    }


    @DeleteMapping("/{ticketId}/project/{projectId}/user/{userId}")
    @PreAuthorize("hasAuthority('developer:edit')")
    public void deleteTicket(@PathVariable("projectId") Integer projectId,
                             @PathVariable("ticketId") Integer ticketId,
                             @PathVariable("userId") Integer userId) {
        Project project = projectService.findProjectById(projectId);
        Developer developer = developerService.findUserById(userId);
        developer.getTickets().remove(ticketService.findTicketById(ticketId));
        Ticket ticket = project.getTickets().stream()
                .filter(t -> t.getId() == (ticketId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Ticket not found"));

        // Test and see if i can do it another way
        project.getTickets().remove(ticket);
        ticketService.deleteTicketById(ticketId);
    }

    // Make a new one, where it dousnt do all that.. make it simplir wheere it only uses the ticketID and then start removing
    // then make a remove dev from ticket.. :D

    @DeleteMapping("/{ticketId}/project/{projectId}")
    @PreAuthorize("hasAuthority('developer:edit')")
    public void deleteTicket(@PathVariable("projectId") Integer projectId,
                             @PathVariable("ticketId") Integer ticketId){

        Project project = projectService.findProjectById(projectId);
        Ticket ticket = ticketService.findTicketById(ticketId);

        project.getTickets().remove(ticket);

        for (Developer dev : ticket.getDevelopers()){
            Developer developer = developerService.findUserById(dev.getId());
            developer.getTickets().remove(ticket);
        }

        ticket.getDevelopers().clear();

        ticketService.deleteTicketById(ticketId);
    }



    @GetMapping("/{ticketId}")
    @PreAuthorize("hasAuthority('developer:read')")
    public TicketDTO getTicketById(@PathVariable("ticketId") Integer ticketId){
        return ticketService.fromEntityToDTO(ticketService.findTicketById(ticketId));
    }


    @PutMapping("/{ticketId}/project/{projectId}")
    @PreAuthorize("hasAuthority('developer:edit')")
    public TicketDTO editTicket(@PathVariable("projectId") Integer projectId,
                             @PathVariable("ticketId") Integer ticketId,
                             @RequestBody Ticket newTicket) {
        Project project = projectService.findProjectById(projectId);
        Ticket ticket = project.getTickets().stream()
                .filter(t -> t.getId() == (ticketId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Ticket not found"));

        ticket.setTitle(newTicket.getTitle());
        ticket.setDescription(newTicket.getDescription());
        ticket.setPriority(newTicket.getPriority());
        ticket.setType(newTicket.getType());
        ticket.setStatus(newTicket.getStatus());

        return ticketService.fromEntityToDTO(ticketService.saveTicket(ticket));
    }

    @PutMapping("/{ticketId}/user/{userId}")
    @PreAuthorize("hasAuthority('developer:edit')")
    public TicketDTO addDev(@PathVariable("ticketId") Integer ticketId,
                                 @PathVariable("userId") Integer userId){

        Developer developer = developerService.findUserById(userId);
        Ticket ticket = ticketService.findTicketById(ticketId);

        ticket.getDevelopers().add(developer);
        developer.getTickets().add(ticket);

        return ticketService.fromEntityToDTO(ticketService.saveTicket(ticket));

    }

    @PutMapping("/{ticketId}/user/{userId}/unassign")
    @PreAuthorize("hasAuthority('developer:edit')")
    public TicketDTO unassignDev(@PathVariable("ticketId") Integer ticketId,
                               @PathVariable("userId") Integer userId){

        Developer developer = developerService.findUserById(userId);
        Ticket ticket = ticketService.findTicketById(ticketId);

        ticket.getDevelopers().remove(developer);
        developer.getTickets().remove(ticket);

        return ticketService.fromEntityToDTO(ticketService.saveTicket(ticket));

    }

}
