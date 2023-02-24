package com.booking.system.service.api;

import com.booking.system.model.dto.TicketDTO;

import java.util.List;

public interface TicketService {
    TicketDTO insertNewTicket(TicketDTO ticketDTO);

    List<TicketDTO> getAllTicketsByUserEmail(String email);

    void deleteTicket(String busName, long seat);
}
