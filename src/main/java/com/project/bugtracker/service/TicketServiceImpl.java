package com.project.bugtracker.service;

import com.project.bugtracker.DTO.TicketDTO;
import com.project.bugtracker.DTO.TicketDTOMapper;
import com.project.bugtracker.Repository.TicketRepository;
import com.project.bugtracker.model.Ticket;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TicketServiceImpl implements TicketService{

    private final TicketRepository ticketRepository;
    private final TicketDTOMapper ticketDTOMapper;

    @Override
    public Ticket findTicketById(Integer ticketId) {
        return ticketRepository.findById(ticketId).get();
    }

    @Override
    public Ticket saveTicket(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    @Override
    public void deleteTicketById(Integer ticketId) {
        ticketRepository.deleteById(ticketId);
    }

    @Override
    public TicketDTO fromEntityToDTO(Ticket ticket) {
        return new TicketDTOMapper().apply(ticket);
    }

    @Override
    public List<TicketDTO> retrieveAllTickets() {
        return ticketRepository.findAll()
                .stream()
                .map(ticketDTOMapper)
                .collect(Collectors.toList());
    }
}
