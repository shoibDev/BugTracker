package com.project.bugtracker.DTO;

import com.project.bugtracker.model.Developer;
import com.project.bugtracker.model.Ticket;
import org.springframework.stereotype.Service;

import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class TicketDTOMapper implements Function<Ticket, TicketDTO> {
    @Override
    public TicketDTO apply(Ticket ticket) {
        return new TicketDTO(
                ticket.getId(),
                ticket.getTitle(),
                ticket.getDescription(),
                ticket.getAuthor(),
                ticket.getPriority(),
                ticket.getType(),
                ticket.getStatus(),
                ticket.getProject().getId(),
                ticket.getDevelopers().stream()
                        .map(Developer::getId)  // Map Developer objects to their IDs
                        .collect(Collectors.toList())
        );
    }
}
