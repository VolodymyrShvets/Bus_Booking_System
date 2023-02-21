package com.booking.system.service;

import com.booking.system.model.Ticket;
import com.booking.system.model.dto.TicketDTO;
import com.booking.system.repository.TicketRepository;
import com.booking.system.service.api.TicketService;
import com.booking.system.service.mapper.TicketMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class TicketServiceImpl implements TicketService {
    private TicketRepository repository;

    @Override
    public TicketDTO insertNewTicket(TicketDTO ticketDTO) {
        log.info("Inserting new Ticket for Bus {} User {} and seat {}",
                ticketDTO.getBusName(), ticketDTO.getUserEmail(), ticketDTO.getSeat());

        Ticket ticket = TicketMapper.INSTANCE.ticketDTOToTicket(ticketDTO);
        repository.insert(ticket);

        log.info("Ticket for Bus {} User {} and seat {} successfully inserted",
                ticketDTO.getBusName(), ticketDTO.getUserEmail(), ticketDTO.getSeat());
        return TicketMapper.INSTANCE.ticketToTicketDTO(ticket);
    }

    @Override
    public List<TicketDTO> getAllTicketsByUserEmail(String email) {
        log.info("Receiving all tickets for user {}", email);
        List<Ticket> tickets = repository.findAllByUserEmail(email);

        return tickets
                .stream()
                .map(Ticket::updateTicketStatus)
                .map(TicketMapper.INSTANCE::ticketToTicketDTO)
                .collect(Collectors.toList());
    }
}
