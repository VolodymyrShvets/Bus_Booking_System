package com.booking.system.service.mapper;

import com.booking.system.model.Ticket;
import com.booking.system.model.dto.TicketDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TicketMapper {
    TicketMapper INSTANCE = Mappers.getMapper(TicketMapper.class);

    @Mapping(target = "busName", source = "ticket.busName")
    @Mapping(target = "busDepartureCity", source = "ticket.busDepartureCity")
    @Mapping(target = "busArrivalCity", source = "ticket.busArrivalCity")
    @Mapping(target = "busDepartureTime", source = "ticket.busDepartureTime")
    @Mapping(target = "busArrivalTime", source = "ticket.busArrivalTime")
    @Mapping(target = "seat", source = "ticket.seat")
    @Mapping(target = "price", source = "ticket.price")
    @Mapping(target = "userFirstName", source = "ticket.userFirstName")
    @Mapping(target = "userLastName", source = "ticket.userLastName")
    @Mapping(target = "userEmail", source = "ticket.userEmail")
    @Mapping(target = "status", source = "ticket.status")
    TicketDTO ticketToTicketDTO(Ticket ticket);

    @Mapping(target = "busName", source = "dto.busName")
    @Mapping(target = "busDepartureCity", source = "dto.busDepartureCity")
    @Mapping(target = "busArrivalCity", source = "dto.busArrivalCity")
    @Mapping(target = "busDepartureTime", source = "dto.busDepartureTime")
    @Mapping(target = "busArrivalTime", source = "dto.busArrivalTime")
    @Mapping(target = "seat", source = "dto.seat")
    @Mapping(target = "price", source = "dto.price")
    @Mapping(target = "userFirstName", source = "dto.userFirstName")
    @Mapping(target = "userLastName", source = "dto.userLastName")
    @Mapping(target = "userEmail", source = "dto.userEmail")
    @Mapping(target = "status", source = "dto.status")
    Ticket ticketDTOToTicket(TicketDTO dto);
}
