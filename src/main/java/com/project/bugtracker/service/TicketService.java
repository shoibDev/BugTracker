package com.project.bugtracker.service;

import com.project.bugtracker.DTO.DeveloperDTO;
import com.project.bugtracker.DTO.TicketDTO;
import com.project.bugtracker.model.Developer;
import com.project.bugtracker.model.Ticket;

import java.util.List;

public interface TicketService {

    Ticket findTicketById(Integer ticketId);

    Ticket saveTicket(Ticket ticket);

    void deleteTicketById(Integer ticketId);


    TicketDTO fromEntityToDTO(Ticket ticket);

    List<TicketDTO> retrieveAllTickets();
}
