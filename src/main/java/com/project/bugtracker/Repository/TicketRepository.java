package com.project.bugtracker.Repository;

import com.project.bugtracker.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TicketRepository extends JpaRepository<Ticket, Integer> {

}
