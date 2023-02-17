package com.booking.system.model.dto;

import com.booking.system.model.TicketStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class TicketDTO {
    private String busName;
    private String busDepartureCity;
    private String busArrivalCity;
    private LocalDateTime busDepartureTime;
    private LocalDateTime busArrivalTime;
    private long seat;
    private long price;
    private String userFirstName;
    private String userLastName;
    private String userEmail;
    private TicketStatus status;
}
