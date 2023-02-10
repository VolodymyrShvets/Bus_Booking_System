package com.booking.system.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Setter
@Getter
public class BusDTO {
    private String name;
    private String departureCity;
    private String arrivalCity;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private LocalTime travelTime;
    private List<Long> availableSeats;
    private long ticketPrice;
}
