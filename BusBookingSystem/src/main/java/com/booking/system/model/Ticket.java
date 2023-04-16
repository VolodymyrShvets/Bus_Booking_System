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
    private int seat;
    private long price;
    private String userFirstName;
    private String userLastName;
    private String userEmail;
    private TicketStatus status;

    public boolean updateTicketStatus() {
        if (this.status == TicketStatus.ACTIVE)
            if (this.busArrivalTime.isBefore(LocalDateTime.now())) {
                this.status = TicketStatus.EXPIRED;
                return true;
            }
        return false;
    }
}
