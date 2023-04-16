package com.booking.system.service.api;

import com.booking.system.model.dto.TicketDTO;

import java.util.List;

public interface TicketService {
    TicketDTO insertNewTicket(TicketDTO ticketDTO);

    TicketDTO getTicketById(String id);

    List<TicketDTO> getAllTicketsByUserEmail(String email);

    void returnTicket(String id);

    void deleteTicket(String busName, long seat);
}
