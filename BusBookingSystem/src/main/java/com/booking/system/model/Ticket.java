package com.booking.system.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Setter
@Getter
@Component
@Document
@ToString
public class Ticket {
    @Id
    private String id;
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
