package com.booking.system.service.impl;

import com.booking.system.model.Ticket;
import com.booking.system.model.dto.TicketDTO;
import com.booking.system.repository.TicketRepository;
import com.booking.system.service.api.TicketService;
import com.booking.system.service.mapper.TicketMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.Period;
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
    public TicketDTO getTicketById(String id) {
        log.info("Receiving ticket with id {}", id);
        return TicketMapper.INSTANCE.ticketToTicketDTO(repository.findById(id).get());
    }

    @Override
    public List<TicketDTO> getAllTicketsByUserEmail(String email) {
        log.info("Receiving all tickets for user {}", email);
        List<Ticket> tickets = repository.findAllByUserEmail(email);

        deleteExpiredTickets(tickets);
        updateTicketStatus(tickets);

        return tickets
                .stream()
                .map(TicketMapper.INSTANCE::ticketToTicketDTO)
                .collect(Collectors.toList());
    }

    private void updateTicketStatus(List<Ticket> tickets) {
        for (Ticket t : tickets) {
            if (t.updateTicketStatus())
                repository.save(t);
        }
    }

    private void deleteExpiredTickets(List<Ticket> tickets) {
        LocalDateTime thirtyDaysAgo = LocalDateTime.now().minus(Period.ofDays(30));

        for (Ticket t : tickets) {
            if (t.getBusArrivalTime().isEqual(thirtyDaysAgo)) {
                repository.delete(t);
                tickets.remove(t);
            }
        }
    }

    @Override
    public void deleteTicket(String busName, long seat) {
        log.info("deleting ticket with bus name {} and seat {}", busName, seat);
        repository.deleteTicketByBusNameAndSeat(busName, seat);
    }
}
