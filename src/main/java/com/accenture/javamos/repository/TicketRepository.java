package com.accenture.javamos.repository;

import com.accenture.javamos.entity.Ticket;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, String> {
  Optional<Ticket> findById(String id);
}
