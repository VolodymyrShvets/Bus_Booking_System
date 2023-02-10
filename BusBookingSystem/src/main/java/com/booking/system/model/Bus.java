package com.booking.system.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Setter
@Getter
@EqualsAndHashCode
@Component
@Document
public class Bus {
    @Id
    private String id;
    @Indexed(unique = true)
    private String name;
    private String departureCity;
    private String arrivalCity;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private LocalTime travelTime;
    private List<Long> availableSeats;
    private long ticketPrice;

    public void setTravelTime() {
        if (departureTime != null && arrivalTime != null) {
            LocalDateTime tempDateTime = LocalDateTime.from(departureTime);

            long hours = tempDateTime.until(arrivalTime, ChronoUnit.HOURS);
            tempDateTime = tempDateTime.plusHours(hours);
            long minutes = tempDateTime.until(arrivalTime, ChronoUnit.MINUTES);
            tempDateTime = tempDateTime.plusMinutes(minutes);

            travelTime = LocalTime.of((int) hours, (int) minutes);
        }
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd-MM-yyyy");

        return "Bus: [" +
                "\n\tid = " + id +
                ",\n\tname = '" + name + '\'' +
                ",\n\tsendingStation = '" + departureCity + '\'' +
                ",\n\tarrivalStation = '" + arrivalCity + '\'' +
                ",\n\tdateOfDeparture = " + departureTime.format(formatter) +
                ",\n\tdateOfArrival = " + arrivalTime.format(formatter) +
                ",\n\ttravelTime = " + travelTime +
                ",\n\tavailableSeats: \n\t\t" + availableSeats +
                ",\n\tticketPrice = " + ticketPrice +
                "\n]";
    }
}
