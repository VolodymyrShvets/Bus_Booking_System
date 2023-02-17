package com.booking.system.repository;

import com.booking.system.model.Ticket;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends MongoRepository<Ticket, String> {
    Ticket findByBusName(String busName);

    List<Ticket> findAllByUserEmail(String email);
}
